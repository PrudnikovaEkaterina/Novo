package ru.dom_novo.api.tests.profileApiTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;
import ru.dom_novo.api.steps.profileApiSteps.ProfileApi;
import ru.dom_novo.testData.GenerationData;
@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class ProfileApiTests {
    String phone= GenerationData.setRandomPhoneNumber();
    String accessToken = AuthApiSteps.getAccessToken(phone);

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
