package ru.prudnikova.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.aeonbits.owner.ConfigCache;
import org.openqa.selenium.Cookie;
import ru.prudnikova.config.AuthConfig;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class AuthPage {
    private final SelenideElement
            PHONE_INPUT = $x("//input[@type='tel']"),
            AUTH_USER_BY_PHONE_CONFIRM_LINK = $(".el-checkbox__inner"),
            SEND_CODE_BUTTON = $x("//span[text()='Отправить код для входа ']"),
            PASSWORD_INPUT= $x("//input[@type='text']"),
            USER_NAME_INPUT=$x("//input[@type='text']"),
            AUTH_TITLE=$(".complete-auth__title"),
            COMPLETE_AUTH_NAME_BUTTON = $(".complete-auth__submit");

    AuthConfig authConfig = ConfigCache.getOrCreate(AuthConfig.class);

    public void openAuthPage() {
        open(baseUrl);
        Cookie authCookie = new Cookie(authConfig.authCookieName(), authConfig.authCookieValue());
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        open("/auth");
    }


    public AuthPage setPhone(String phone) {
        PHONE_INPUT.setValue(phone);
        return this;
    }

    public AuthPage clickAuthUserByPhoneConfirmLink() {
        AUTH_USER_BY_PHONE_CONFIRM_LINK.click();
        return this;
    }

    public AuthPage clickButtonSendCode() {
        SEND_CODE_BUTTON.shouldBe(Condition.enabled).click();
        return this;
    }

    public AuthPage setSmsCode (String smsCode) {
        PASSWORD_INPUT.setValue(smsCode);
        return this;
    }

    public AuthPage checkAuthTitle () {
        AUTH_TITLE.shouldHave(Condition.text("Как к Вам обращаться?"));
        return this;
    }


    public AuthPage setUserName (String name) {
        USER_NAME_INPUT.setValue(name);
        return this;
    }

    public void clickCompleteAuthNameButton () {
        COMPLETE_AUTH_NAME_BUTTON.click();
    }



}
