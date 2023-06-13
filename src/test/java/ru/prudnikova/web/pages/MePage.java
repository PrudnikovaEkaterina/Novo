package ru.prudnikova.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Cookie;
import ru.prudnikova.api.steps.authSteps.AuthApi;

import java.sql.Timestamp;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MePage {

    private final SelenideElement
            INPUT_NAME = $x("//input[@name='name']"),
            INPUT_EMAIL = $x("//input[@name='email']"),
            SAVE_СHANGES = $(".me-settings__chosen-icon");

    public void openMePageWithApiAuth(String phoneNumber) {
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
        open("https://novo-dom.ru/me");
    }

    public void changeUserName(String userName) {
        INPUT_NAME.setValue(userName);
        SAVE_СHANGES.click();
    }

    public MePage changeUserEmail(String email) {
        INPUT_EMAIL.setValue(email);
        SAVE_СHANGES.click();
        return this;
    }

    public void verifyChangeUserEmail(String email) {
        INPUT_EMAIL.shouldHave(Condition.value(email));
    }

}
