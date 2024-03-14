package ru.dom_novo.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigCache;
import org.openqa.selenium.Cookie;
import ru.dom_novo.config.AuthConfig;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class AuthPage {
    private final SelenideElement
            PHONE_INPUT = $x("//input[@type='tel']"),
            CHECKBOX_AUTH_USER_BY_PHONE_CONFIRM = $(".el-checkbox__inner"),
            SEND_CODE_BUTTON = $x("//span[text()='Отправить код для входа ']"),
            PASSWORD_INPUT = $x("//input[@type='text']"),
            USER_NAME_INPUT = $x("//input[@type='text']"),
            AUTH_TITLE = $(".complete-auth__title"),
            COMPLETE_AUTH_NAME_BUTTON = $(".complete-auth__submit");

    AuthConfig authConfig = ConfigCache.getOrCreate(AuthConfig.class);

    @Step("Open the login page with pre-installed cookies")
    public void openAuthPage() {
        open(baseUrl);
        Cookie authCookie = new Cookie(authConfig.authCookieName(), authConfig.authCookieValue());
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        open("/auth");
    }

    @Step("Set the phone number in the input field")
    public AuthPage setPhone(String phone) {
        sleep(1000);
        PHONE_INPUT.setValue(phone);
        return this;
    }

    @Step("Check the box I agree to the terms of use")
    public AuthPage clickCheckboxAuthConfirm() {
        CHECKBOX_AUTH_USER_BY_PHONE_CONFIRM.click();
        return this;
    }

    @Step("Click on the Send SMS code button")
    public AuthPage clickButtonSendCode() {
        SEND_CODE_BUTTON.shouldBe(Condition.enabled).click();
        return this;
    }

    @Step("Set the received SMS code in the input field")
    public AuthPage setSmsCode(String smsCode) {
        PASSWORD_INPUT.setValue(smsCode);
        return this;
    }

    @Step("Check title display How to contact you")
    public AuthPage checkAuthTitle() {
        String completeAuthTitle = "Как к Вам обращаться?";
        AUTH_TITLE.shouldHave(Condition.text(completeAuthTitle));
        return this;
    }

    @Step("Set username in the input field")
    public AuthPage setUserName(String name) {
        USER_NAME_INPUT.setValue(name);
        return this;
    }

    @Step("Click complete auth name button")
    public void clickCompleteAuthNameButton() {
        COMPLETE_AUTH_NAME_BUTTON.click();
    }

}
