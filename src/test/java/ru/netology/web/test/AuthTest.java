package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataBase;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.data.DataHelper.*;

public class AuthTest {
    LoginPage loginPage;
    VerificationPage verificationPage;
    DashboardPage dashboardPage;


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        loginPage = new LoginPage();
    }

    @AfterAll
    public static void CleanAll()  {
        DataBase.cleanAll();
    }

    @Test
    public void shouldAuth() {
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
