package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApi;
import ru.dom_novo.api.steps.meApiSteps.MeApiSteps;
import ru.dom_novo.dataBase.dao.FavoritesDao;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesItemsDateAdd")
public class FavoritesItemsDateTests extends TestBase {
    FavoritesPage favoritesPage = new FavoritesPage();
    String phoneNumber = GenerationData.setRandomUserPhone();

    @BeforeEach
    void beforeEach() throws InterruptedException {
        UserFavoritesApi.addBuildingToUserFavorites(phoneNumber);
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle();
    }

    @Test
    @DisplayName("Проверить дату добавления ЖК на странице Мое избранное")
    void checkFavoritesBuildingsDateAdd() {
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<String> buildingsDateAddExpected = FavoritesDao.selectFavoritesBuildingsUpdatedAt(userId);
        List<String> buildingsDateAddActual = favoritesPage.getBuildingsDateText();
        assertThat(buildingsDateAddActual, is(buildingsDateAddExpected));
    }
}
