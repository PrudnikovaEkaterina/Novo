package ru.prudnikova.web.pages.components;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CallMeWidget {
    private final SelenideElement
            PHONE_INPUT = $x("//input[@name='phone']"),
            CALLBACK_PHONE_MODAL_TITLE = $(".callback-phone-modal__title"),
            CALL_ME_BUTTON = $(".call-me__text"),
            PHONE_THANKS_MODAL_TITLE = $(".phone-thanks-modal__title");

    public CallMeWidget verifyCallbackPhoneModalTitle(String callbackPhoneModalTitle) {
        Assertions.assertEquals(callbackPhoneModalTitle, CALLBACK_PHONE_MODAL_TITLE.getText());
        return this;
    }

    public CallMeWidget setPhoneNumber(String phoneNumber) {
        PHONE_INPUT.setValue(phoneNumber);
        return this;
    }

    public CallMeWidget clickCallMeButton() {
        CALL_ME_BUTTON.click();
        return this;
    }

    public CallMeWidget verifyPhoneThanksModalTitle(String phoneThanksModalTitle) {
        Assertions.assertEquals(phoneThanksModalTitle, PHONE_THANKS_MODAL_TITLE.getText());
        return this;
    }

}
