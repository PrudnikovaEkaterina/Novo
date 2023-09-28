package ru.dom_novo.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class MePage {

    private final SelenideElement
            INPUT_NAME = $x("//input[@name='name']"),
            INPUT_EMAIL = $x("//input[@name='email']"),
            SAVE_CHANGES = $(".me-settings__chosen-icon");

    public void openMePageWithApiAuth(String phoneNumber) {
       AuthApiSteps.setAuthCookiesToBrowser(phoneNumber);
       open(baseUrl + "/me");
    }

    public void changeUserName(String userName) {
        INPUT_NAME.setValue(userName);
        SAVE_CHANGES.click();
    }

    public MePage changeUserEmail(String email) {
        INPUT_EMAIL.setValue(email);
        SAVE_CHANGES.click();
        return this;
    }

    public void verifyChangeUserEmail(String email) {
        INPUT_EMAIL.shouldHave(Condition.value(email));
    }

}
