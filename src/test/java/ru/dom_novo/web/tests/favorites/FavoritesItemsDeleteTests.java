package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApi;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesDeleteItem")
public class FavoritesItemsDeleteTests extends TestBase {
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
    @DisplayName("Проверить, что ЖК перестает отображаться в списке Мое избранное после удаления объекта из списка")
    void checkDeleteBuildingFromFavoritesAfterClickFavoriteIcon () throws InterruptedException {
        List<String> listBuildingsTitleBefore = favoritesPage.getBuildingsTitleEng();
        String firstSearchBuildingsTitleEng = listBuildingsTitleBefore.get(0);
        favoritesPage.clickFavoriteIconForFirstSearchBuilding();
        List<String> listBuildingsTitleEngAfter = favoritesPage.getBuildingsTitleEng();
        assertThat(listBuildingsTitleEngAfter, not(hasItem(firstSearchBuildingsTitleEng)));
    }

    @Test
    @TmsLink("https://tracker.yandex.ru/NOVODEV-629")
    @DisplayName("Проверить, что значение счетчика ЖК на странице Мое избранное уменьшается на 1 после удаления объекта из списка")
    void checkFavoritesBuildingsCount () throws InterruptedException {
        int favoritesBuildingsCountBefore = favoritesPage.getFavoritesBuildingsCount();
        favoritesPage.clickFavoriteIconForFirstSearchBuilding();
        int favoritesBuildingsCountAfter = favoritesPage.getFavoritesBuildingsCount();
        Assertions.assertEquals(1, favoritesBuildingsCountBefore-favoritesBuildingsCountAfter);
    }
}
