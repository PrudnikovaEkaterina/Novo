package ru.prudnikova.api.tests.favoritesTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.models.auth.AuthDto;
import ru.prudnikova.api.steps.authSteps.AuthApi;
import ru.prudnikova.api.steps.favoritesSteps.UserFavoritesApi;
import ru.prudnikova.dataBase.managers.FavoritesManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class UserFavoritesTests {

    @Test
    @Owner("PrudnikovaEkaterina")
    @Tag("Api")
    @DisplayName("Получение списка избранных ЖК пользователя и проверка его на соответсвие данным из БД")
    void checkUserFavoritesBuilding() {
        String phone = "79085040794";
        AuthDto authModel = AuthApi.auth(phone);
        String accessToken = authModel.getAccessToken();
        int userId = authModel.getUser().getId();
        List<Integer> userFavoritesBuildingListActual = UserFavoritesApi.getUserFavoritesBuilding(accessToken);
        List<Integer> userFavoritesBuildingListExpected = FavoritesManager.selectEntityIdFromFavoritesForUser(userId);
        assertIterableEquals(userFavoritesBuildingListExpected, userFavoritesBuildingListActual);
    }

}
