package ru.dom_novo.api.tests.authApiTests;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;
import ru.dom_novo.testData.GenerationData;

@Tag("Api")
@Story("ApiAuth")
@Owner("PrudnikovaEkaterina")
public class AuthApiTests {

    String phoneNumber = GenerationData.setRandomPhoneNumber();

    @Test
    @DisplayName("Авторизация пользователя")
    void auth() {
        AuthApiSteps.auth(phoneNumber);

    }

    @Test
    @DisplayName("Логаут пользователя")
    void logout() {
        String token = AuthApiSteps.getAccessToken(phoneNumber);
        AuthApiSteps.logout(token);
    }

}
