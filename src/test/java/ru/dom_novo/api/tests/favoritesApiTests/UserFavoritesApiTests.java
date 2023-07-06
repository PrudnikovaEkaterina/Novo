package ru.dom_novo.api.tests.favoritesApiTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.models.authDto.AuthDto;
import ru.dom_novo.api.steps.authApiSteps.AuthApi;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApi;
import ru.dom_novo.dataBase.dao.FavoritesDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class UserFavoritesApiTests {

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
        List<Integer> userFavoritesBuildingListExpected = FavoritesDao.selectEntityIdFromFavoritesForUser(userId);
        assertIterableEquals(userFavoritesBuildingListExpected, userFavoritesBuildingListActual);
    }

}
