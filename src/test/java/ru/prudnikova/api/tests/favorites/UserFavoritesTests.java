package ru.prudnikova.api.tests.favorites;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;;
import ru.prudnikova.api.steps.auth.AuthSteps;
import ru.prudnikova.api.steps.favorites.UserFavoritesSteps;


public class UserFavoritesTests {


    @Test
    @Owner("PrudnikovaEkaterina")
    @Tag("Api")
    @DisplayName("Получение списка избранных ЖК пользователя")
    void getUserFavoritesBuilding () {
        String phone="79085040794";
        String accessToken = AuthSteps.getAccessToken(phone);
        UserFavoritesSteps.getUserFavoritesBuilding(accessToken);
    }
}
