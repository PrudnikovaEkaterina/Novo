package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApi;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesCallMe")
public class FavoritesCallMeTests extends TestBase {
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
    @DisplayName("Проверить появление callback phone modal после клика на кнопку Заказать звонок на странице Мое избранное")
    void checkFavoritesManagerBlock () {
            favoritesPage
                    .hoverFirstSearchItem()
                    .clickFirstCallMeWidgetButton()
                    .checkCallbackPhoneModalTitle();
    }
}
