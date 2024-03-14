package ru.dom_novo.web.tests.favorites;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApiSteps;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.pages.MainPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesDeleteItem")
public class FavoritesItemsDeleteTests extends TestBase {
    FavoritesPage favoritesPage = new FavoritesPage();
    MainPage mainPage = new MainPage();
    String phoneNumber = GenerationData.setRandomUserPhone();

    @BeforeEach
    void beforeEach() {
       UserFavoritesApiSteps.addBuildingToUserFavoritesUsePhoneNumber(phoneNumber);
        favoritesPage
                .openFavoritesPageWithAuthUsePhoneNumber(phoneNumber)
                .checkFavoritesHeaderTitle();
    }

    @Test
    @DisplayName("Проверить, что ЖК перестает отображаться в списке Мое избранное после удаления объекта из списка")
    void checkDeleteBuildingFromFavoritesAfterClickFavoriteIcon() throws InterruptedException {
            List<String> listBuildingsTitleBefore = favoritesPage.collectBuildingsTitleEng();
            String firstSearchBuildingsTitleEng = listBuildingsTitleBefore.get(0);
            favoritesPage.clickFavoriteIconForFirstSearchBuilding();
            Selenide.sleep(1000);
            List<String> listBuildingsTitleEngAfter = favoritesPage.collectBuildingsTitleEng();
            assertThat(listBuildingsTitleEngAfter, not(hasItem(firstSearchBuildingsTitleEng)));
    }

    @Test
    @TmsLink("https://tracker.yandex.ru/NOVODEV-629")
    @DisplayName("Проверить, что значение счетчика ЖК на странице Мое избранное уменьшается на 1 после удаления объекта из списка")
    void checkFavoritesBuildingsCount() throws InterruptedException {
        int favoritesBuildingsCountBefore = favoritesPage.getFavoritesBuildingsCount();
        favoritesPage.clickFavoriteIconForFirstSearchBuilding();
        int favoritesBuildingsCountAfter = favoritesPage.getFavoritesBuildingsCount();
        assertThat(favoritesBuildingsCountBefore - favoritesBuildingsCountAfter, is(1));
    }
}
