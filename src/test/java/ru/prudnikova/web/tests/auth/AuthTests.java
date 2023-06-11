package ru.prudnikova.web.tests.auth;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigCache;
import org.junit.jupiter.api.*;
import ru.prudnikova.config.AuthConfig;
import ru.prudnikova.testData.GenerationData;
import ru.prudnikova.web.pages.AuthPage;
import ru.prudnikova.web.pages.components.Header;
import ru.prudnikova.web.tests.TestBase;

@Tag("Web")
@Story("Auth")
public class AuthTests extends TestBase {
    AuthPage authPage = new AuthPage();
    Header header = new Header();
    AuthConfig authConfig = ConfigCache.getOrCreate(AuthConfig.class);

    @BeforeEach
    void beforeEach() {
        authPage.openAuthPage();
    }

    @AfterEach
    void afterEach() {
        header.logout();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Позитивная проверка регистрации нового пользователя")
    void registrationNewUserSuccessful() {
        String phoneNumber = GenerationData.setRandomPhoneNumber();
        String smsCode = authConfig.smsCode();
        String userName = GenerationData.setRandomUserName();
        authPage.
                setPhone(phoneNumber).clickAuthUserByPhoneConfirmLink().clickButtonSendCode().setSmsCode(smsCode)
                .checkAuthTitle().setUserName(userName).clickCompleteAuthNameButton();
        header.checkAccountName(userName);
        header.hoverHeaderAccountIconAndCheckUserMenuDropdownText();

    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Позитивная проверка авторизации пользователя")
    void authUserSuccessful() {
        String phoneNumber = "79085040794";
        String smsCode = authConfig.smsCode();
        authPage.
                setPhone(phoneNumber).clickAuthUserByPhoneConfirmLink().clickButtonSendCode().setSmsCode(smsCode);
        header.hoverHeaderAccountIconAndCheckUserMenuDropdownText();

    }
}
