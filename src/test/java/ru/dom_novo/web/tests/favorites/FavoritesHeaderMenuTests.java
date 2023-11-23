package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.meApiSteps.MeApiSteps;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesHeaderMenu")
public class FavoritesHeaderMenuTests extends TestBase {
    FavoritesPage favoritesPage = new FavoritesPage();
    String phoneNumber = GenerationData.setRandomUserPhone();

    @BeforeEach
    void beforeEach() {
//        UserFavoritesApi.addBuildingToUserFavoritesUsePhoneNumber(phoneNumber);
        favoritesPage
                .openFavoritesPageWithAuthUsePhoneNumber(phoneNumber)
                .checkFavoritesHeaderTitle();
    }

    @Test
    @DisplayName("Проверить содержание header menu страницы Мое избранное")
    void checkFavoritesHeaderMenu () {
        favoritesPage.checkFavoritesHeaderMenu();
    }

    @Test
    @DisplayName("Проверить значение счетчика Жилые комплексы в header menu страницы Мое избранное")
    void checkFavoritesBuildingsCount () {
        int countFavoritesBuildingsExpected = MeApiSteps.getFavoritesBuildingsCount(phoneNumber);
        int countFavoritesBuildingsActual = favoritesPage.getFavoritesBuildingsCount();
        assertThat(countFavoritesBuildingsActual, is(countFavoritesBuildingsExpected));
    }

    @Test
    @DisplayName("Проверить значение счетчика Квартиры в header menu страницы Мое избранное")
    void checkFavoritesFlatsCount () {
        int countFavoritesFlatsExpected = MeApiSteps.getFavoritesFlatsCount(phoneNumber);
        int countFavoritesFlatsActual = favoritesPage.getFavoritesFlatsCount();
        assertThat(countFavoritesFlatsActual, is(countFavoritesFlatsExpected));
    }

    @Test
    @DisplayName("Проверить значение счетчика Рекомендации менеджера в header menu страницы Мое избранное")
    void checkRecommendationsCount () {
        int countRecommendationsExpected = MeApiSteps.getRecommendationsCount(phoneNumber);
        int countRecommendationsActual = favoritesPage.getRecommendationsCount();
        assertThat(countRecommendationsActual, is(countRecommendationsExpected));
    }
}
