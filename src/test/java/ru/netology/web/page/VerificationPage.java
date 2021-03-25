package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    public final SelenideElement paragraph = $(".paragraph>.icon");
    public final SelenideElement fieldCode = $("[data-test-id=\"code\"] .input__control");
    public final SelenideElement confirmButton = $("[data-test-id=\"action-verify\"]");

    public VerificationPage() {
        paragraph.shouldBe(visible);
    }

    public void clickConfirmButton() {
        confirmButton.click();
    }

    public void enterCode(String code) {
        fieldCode.sendKeys(code);
    }
}
