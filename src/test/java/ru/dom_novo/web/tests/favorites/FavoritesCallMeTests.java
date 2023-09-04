package ru.dom_novo.web.tests.favorites;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApi;
import ru.dom_novo.regexp.RegexpMeth;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.pages.components.CallMeWidgetComponent;
import ru.dom_novo.web.tests.TestBase;

import static com.codeborne.selenide.Selenide.*;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesCallMe")
public class FavoritesCallMeTests extends TestBase {
    FavoritesPage favoritesPage = new FavoritesPage();
    CallMeWidgetComponent callMeWidgetComponent = new CallMeWidgetComponent();
    String phoneNumber = GenerationData.setRandomPhoneNumber();

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

    @Test
    @DisplayName("Проверить, что в callback phone modal отображается телефон пользователя")
    void checkPhoneInputValue () {
        String expectedPhone = phoneNumber;
        favoritesPage
                .hoverFirstSearchItem()
                .clickFirstCallMeWidgetButton();
        String actualPhone = callMeWidgetComponent.getPhoneInputValue();
        String actualPhoneFormatted = RegexpMeth.getAllNumbersFromString(actualPhone);
        Assertions.assertEquals(expectedPhone, actualPhoneFormatted);
    }
}
