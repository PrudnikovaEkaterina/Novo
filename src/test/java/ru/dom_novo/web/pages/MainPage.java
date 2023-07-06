package ru.dom_novo.web.pages;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Cookie;
import ru.dom_novo.api.steps.authApiSteps.AuthApi;

import java.sql.Timestamp;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MainPage {
    public void openMainPage() {
        open(baseUrl);
    }
    public void openMainPageWithApiAuth(String phoneNumber) {
        String refreshToken = AuthApi.getRefreshToken(phoneNumber);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long timestampTime = timestamp.getTime();
        long session = timestampTime + 2592000;
        String sessionExpiresAt = String.valueOf(session).substring(0, 10);

        open("https://novo-dom.ru/build/desktop/images/header-logo.c8b95b60.svg");
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        getWebDriver().manage().addCookie(cookie);
        Selenide.localStorage().setItem("session_expires_at", sessionExpiresAt);
        Selenide.refresh();
        open(baseUrl);
    }
}
