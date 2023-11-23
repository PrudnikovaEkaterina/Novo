package ru.dom_novo.api.tests.favoritesApiTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApiSteps;
import ru.dom_novo.api.steps.meApiSteps.MeApiSteps;
import ru.dom_novo.dataBase.dao.FavoritesDao;
import ru.dom_novo.testData.GenerationData;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserFavoritesApiTests {

    @Test
    @Owner("PrudnikovaEkaterina")
    @Tag("Api")
    @DisplayName("Получение списка избранных ЖК пользователя и проверка его на соответсвие данным из БД")
    void checkUserFavoritesBuilding() {
        String phoneNumber = GenerationData.setRandomUserPhone();
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<Integer> userFavoritesBuildingListActual = UserFavoritesApiSteps.getUserFavoritesBuildingsIdList(phoneNumber);
        List<Integer> userFavoritesBuildingListExpected = FavoritesDao.selectBuildingIdFromFavorites(userId);
        assertThat(userFavoritesBuildingListActual, is(userFavoritesBuildingListExpected));
    }

}
