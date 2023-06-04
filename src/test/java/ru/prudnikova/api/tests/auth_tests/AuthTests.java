package ru.prudnikova.api.tests.auth_tests;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.steps.auth_steps.AuthSteps;

@Tag("Api")
@Story("ApiAuth")
public class AuthTests {

    String phoneNumber = "79085040794";

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Авторизация пользователя")
    void auth() {
        AuthSteps.auth(phoneNumber);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Логаут пользователя")
    void logout() {
        String token = AuthSteps.getAccessToken(phoneNumber);
        AuthSteps.logout(token);
    }

}
