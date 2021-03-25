package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataBase;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.data.DataHelper.*;

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
    public void shouldGetUserInfo() throws SQLException {
        DataBase.getUsers();
    }

    @Test
    public void shouldReturnUser() throws SQLException {
     DataHelper.UserInfo result = DataHelper.getUserByLogin("vasya");
     System.out.println(result);
    }

    @Test
    public void shouldGetCode() throws SQLException {
        System.out.println("=" + DataBase.getAuthCode("8f6b8826-fdd4-4249-ab4a-9419ab4873e4"));
    }

    @Test
    public void shouldDeleteFromTable() throws SQLException {
        DataBase.cleanAll();
    }

    @Test
    public void shouldAuth() throws SQLException {
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
