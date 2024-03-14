package ru.dom_novo.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CallMeWidgetComponent {
    private final SelenideElement
            PHONE_INPUT = $("[placeholder='+7']"),
            CALLBACK_PHONE_MODAL_TITLE = $(".callback-phone-modal__title"),
            CALL_ME_BUTTON = $(".call-me__text"),
            PHONE_THANKS_MODAL_TITLE = $(".phone-thanks-modal__title");

    public CallMeWidgetComponent verifyCallbackPhoneModalTitle(String callbackPhoneModalTitle) {
        CALLBACK_PHONE_MODAL_TITLE.shouldBe(Condition.visible);
        Assertions.assertEquals(callbackPhoneModalTitle, CALLBACK_PHONE_MODAL_TITLE.getText());
        return this;
    }

    @Step("Еnter the phone number in the input field")
    public CallMeWidgetComponent setPhoneNumber(String phoneNumber) {
        PHONE_INPUT.setValue(phoneNumber);
        return this;
    }

    @Step("Click on the Call me button")
    public CallMeWidgetComponent clickCallMeButton() {
        CALL_ME_BUTTON.click();
        return this;
    }

    @Step("Check title in phone thanks modal")
    public void verifyPhoneThanksModalTitle(String phoneThanksModalTitle) {
        Assertions.assertEquals(phoneThanksModalTitle, PHONE_THANKS_MODAL_TITLE.getText());
    }

    @Step("Get value from input field")
    public String getPhoneInputValue() {
        return PHONE_INPUT.getValue();
    }

}
