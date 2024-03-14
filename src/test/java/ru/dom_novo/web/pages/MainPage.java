package ru.dom_novo.web.pages;

import io.qameta.allure.Step;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;


public class MainPage {
    @Step("Open the main page with pre-installed API authorization")
    public void openMainPageWithApiAuth(String phoneNumber) {
        AuthApiSteps.setAuthCookiesToBrowserWithPhoneNumber(phoneNumber);
        open(baseUrl);
    }
}
