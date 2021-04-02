package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataBase;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.RestHandler;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

public class ApplicationTest {

    @AfterAll
    public static void cleanUp() {
        System.out.println("tests are done");
    }

    @Nested
    public class AuthTest {
        LoginPage loginPage;
        VerificationPage verificationPage;
        DashboardPage dashboardPage;

        @BeforeEach
        private void setUp() {
            open("http://localhost:9999");
            loginPage = new LoginPage();
        }

        @Test
        public void shouldAuth()  {
            loginPage.enterLogin(returnLoginForTest());
            loginPage.enterPassword(returnPasswordForTest());
            loginPage.clickConfirmButton();
            verificationPage = new VerificationPage();
            verificationPage.enterCode(getCode(getUserId(returnLoginForTest())));
            verificationPage.clickConfirmButton();
            dashboardPage = new DashboardPage();
        }

        @Test
        public void shouldNotAuthEmptyFields() {
            loginPage.clickConfirmButton();
            loginPage.loginFieldSub.shouldHave(Condition.text(loginPage.fieldSubEmptyText));
            loginPage.passwordFieldSub.shouldHave(Condition.text(loginPage.fieldSubEmptyText));
        }

        @Test
        public void shouldNotAuthEmptyPassword() {
            loginPage.enterLogin(returnLoginForTest());
            loginPage.clickConfirmButton();
            loginPage.loginFieldSub.shouldNotBe(Condition.visible);
            loginPage.passwordFieldSub.shouldHave(Condition.text(loginPage.fieldSubEmptyText));
        }

        @Test
        public void shouldNotAuthEmptyLogin() {
            loginPage.enterPassword(returnPasswordForTest());
            loginPage.clickConfirmButton();
            loginPage.loginFieldSub.shouldHave(Condition.text(loginPage.fieldSubEmptyText));
            loginPage.passwordFieldSub.shouldNotBe(Condition.visible);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class ApiTest {
        private String token;
        private String cardOne;
        private String cardTwo;

        @BeforeAll
        public void setUp() {
            DataHelper.cleanCode();
            token = RestHandler.getAuthToken();
            cardOne = DataHelper.returnCardsForUser().get(0).getNumber();
            cardTwo = DataHelper.returnCardsForUser().get(1).getNumber();
            DataHelper.cleanCode();
        }


        @Test
        public void shouldGetCardInfo() {
            System.out.println("result is = " + RestHandler.getCardInfo(token));
        }

        @Test
        public void shouldTransferSum() {
            int amount = 0;
            int initBalance = DataHelper.returnCardBalance(cardOne);
            String result = RestHandler.transferTo(cardOne, cardTwo, amount, token);
            int expected = initBalance - amount;
            int actual = DataHelper.returnCardBalance(cardOne);
            assertEquals(expected, actual);
            System.out.println(DataHelper.returnCardsForUser());
            System.out.println(result);
        }
    }

}
