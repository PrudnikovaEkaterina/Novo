package ru.prudnikova.api.tests.profileApiTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.steps.authApiSteps.AuthApi;
import ru.prudnikova.api.steps.profileApiSteps.ProfileApi;
import ru.prudnikova.testData.GenerationData;
@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class ProfileApiTests {
    String phone= GenerationData.setRandomPhoneNumber();
    String accessToken = AuthApi.getAccessToken(phone);

    @Test
    @DisplayName("Изменение имени пользователя")
    void changeUsername () {
        String userName= GenerationData.setRandomUserName();
        ProfileApi.changeUsername( accessToken, userName);
    }

    @Test
    @DisplayName("Изменение Email пользователя")
    void changeEmail () {
        String email=GenerationData.setRandomEmail();
        ProfileApi.changeEmail( accessToken, email);
    }

    @Test
    @DisplayName("Изменение имени и Email пользователя")
    void changeUsernameAndEmail () {
        String userName= GenerationData.setRandomUserName();
        String email=GenerationData.setRandomEmail();
        ProfileApi.changeUsernameAndEmail(accessToken, userName, email);
    }

}
