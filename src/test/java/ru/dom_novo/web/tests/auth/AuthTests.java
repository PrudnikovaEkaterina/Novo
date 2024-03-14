package ru.dom_novo.web.tests.auth;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigCache;
import org.junit.jupiter.api.*;
import ru.dom_novo.config.AuthConfig;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.AuthPage;
import ru.dom_novo.web.pages.components.HeaderComponent;
import ru.dom_novo.web.tests.TestBase;


@Tag("Web")
@Story("Auth")
@Owner("PrudnikovaEkaterina")
public class AuthTests extends TestBase {
    AuthPage authPage = new AuthPage();
    HeaderComponent header = new HeaderComponent();
    AuthConfig authConfig = ConfigCache.getOrCreate(AuthConfig.class);

    @BeforeEach
    void beforeEach() {
        authPage.openAuthPage();
    }

    @Test
    @Disabled
    @DisplayName("Позитивная проверка регистрации нового пользователя")
    void registrationNewUserSuccessful() {
        String phoneNumber = GenerationData.setRandomPhoneNumber();
        String smsCode = authConfig.smsCode();
        String userName = GenerationData.setRandomUserName();
        authPage.
                setPhone(phoneNumber)
                .clickCheckboxAuthConfirm()
                .clickButtonSendCode()
                .setSmsCode(smsCode)
                .checkAuthTitle()
                .setUserName(userName)
                .clickCompleteAuthNameButton();
        header.checkAccountName(userName);
        header.hoverHeaderAccountIconAndCheckUserMenuDropdownText();
    }

    @Test
    @DisplayName("Позитивная проверка авторизации пользователя")
    void authUserSuccessful() {
        String phoneNumber = "79085040794";
        String smsCode = authConfig.smsCode();
        authPage.
                setPhone(phoneNumber)
                .clickCheckboxAuthConfirm()
                .clickButtonSendCode()
                .setSmsCode(smsCode);
        header.hoverHeaderAccountIconAndCheckUserMenuDropdownText();

    }
}
