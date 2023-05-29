package ru.prudnikova.api.tests.favorites;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.models.auth.AuthModel;
import ru.prudnikova.api.steps.auth.AuthSteps;
import ru.prudnikova.api.steps.favorites.UserFavoritesSteps;
import ru.prudnikova.data_base.managers.UserFavoritesManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class UserFavoritesTests {

    @Test
    @Owner("PrudnikovaEkaterina")
    @Tag("Api")
    @DisplayName("Получение списка избранных ЖК пользователя")
    void getUserFavoritesBuilding() {
        String phone = "79085040794";
        AuthModel authModel = AuthSteps.auth(phone);
        String accessToken = authModel.getAccessToken();
        int userId = authModel.getUser().getId();
        List<Integer> userFavoritesBuildingListActual = UserFavoritesSteps.getUserFavoritesBuilding(accessToken);
        List<Integer> userFavoritesBuildingListExpected = UserFavoritesManager.selectEntityIdFromFavoritesForUser(userId);
        assertIterableEquals(userFavoritesBuildingListExpected, userFavoritesBuildingListActual);

    }

}
