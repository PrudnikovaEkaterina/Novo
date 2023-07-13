package ru.dom_novo.web.pages;
import ru.dom_novo.api.steps.authApiSteps.AuthApi;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;


public class MainPage {
    public void openMainPageWithApiAuth(String phoneNumber) {
        AuthApi.setAuthCookiesToBrowser(phoneNumber);
        open(baseUrl);
    }
}
