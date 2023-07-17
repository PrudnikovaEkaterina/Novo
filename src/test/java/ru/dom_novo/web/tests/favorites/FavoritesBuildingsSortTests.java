package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.meApiSteps.MeApiSteps;
import ru.dom_novo.dataBase.dao.FavoritesDao;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.List;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesSort")
public class FavoritesBuildingsSortTests extends TestBase {

    FavoritesPage favoritesPage = new FavoritesPage();

    String phoneNumber = GenerationData.setRandomUserPhone();

    @BeforeEach
    void beforeEach() {
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle();
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по убыванию' на странице Мое избранное")
    void checkSortFavorites() {
        String sort = "Цена по убыванию";
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<String> listSortExpected = FavoritesDao.selectFavoritesBuildingsSortPriceDesc(userId);
        favoritesPage.setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        assert listSortExpected.containsAll(listSortActual);
    }

}
