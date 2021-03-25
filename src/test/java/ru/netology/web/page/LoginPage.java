package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    public final SelenideElement heading = $("h2");
    public final SelenideElement paragraph = $("p.paragraph");
    public final SelenideElement loginField = $("[data-test-id=\"login\"] .input__control");
    public final SelenideElement loginFieldSub = $("[data-test-id=\"login\"] .input__sub");
    public final SelenideElement passwordField = $("[data-test-id=\"password\"] .input__control");
    public final SelenideElement passwordFieldSub = $("[data-test-id=\"password\"] .input__sub");
    public final SelenideElement confirmButton = $("[data-test-id=\"action-login\"]");
    public final String fieldSubEmptyText = "Поле обязательно для заполнения";

    public LoginPage() {
        heading.shouldBe(visible);
        paragraph.shouldBe(visible);
    }


    public void clickConfirmButton() {
        confirmButton.click();
    }

    public void enterLogin(String login) {
        loginField.sendKeys(login);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

}
