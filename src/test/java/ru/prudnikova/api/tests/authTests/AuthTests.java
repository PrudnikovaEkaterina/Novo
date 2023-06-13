package ru.prudnikova.api.tests.authTests;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.steps.authSteps.AuthApi;

@Tag("Api")
@Story("ApiAuth")
public class AuthTests {

    String phoneNumber = "79085040794";

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Авторизация пользователя")
    void auth() {
        AuthApi.auth(phoneNumber);

    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Логаут пользователя")
    void logout() {
        String token = AuthApi.getAccessToken(phoneNumber);
        AuthApi.logout(token);
    }

}
