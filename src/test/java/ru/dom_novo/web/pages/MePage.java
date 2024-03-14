package ru.dom_novo.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class MePage {

    private final SelenideElement
            INPUT_NAME = $x("//input[@name='name']"),
            INPUT_EMAIL = $x("//input[@name='email']"),
            SAVE_CHANGES = $(".me-settings__chosen-icon");

    @Step("Open the mу page with pre-installed API authorization")
    public void openMePageWithAuthUsePhoneNumber(String phoneNumber) {
        AuthApiSteps.setAuthCookiesToBrowserWithPhoneNumber(phoneNumber);
        open(baseUrl + "/me");
    }

    @Step("Change username to {userName} in profile")
    public void changeUserName(String userName) {
        INPUT_NAME.setValue(userName);
        SAVE_CHANGES.click();
    }

    @Step("Change email to {email} in profile")
    public MePage changeUserEmail(String email) {
        INPUT_EMAIL.setValue(email);
        SAVE_CHANGES.click();
        return this;
    }

    @Step("Check that the user's email has been changed to {email}")
    public void verifyChangeUserEmail(String email) {
        INPUT_EMAIL.shouldHave(Condition.value(email));
    }
}
