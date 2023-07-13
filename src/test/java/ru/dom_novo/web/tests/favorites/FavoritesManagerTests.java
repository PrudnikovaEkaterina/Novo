package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.meApiSteps.MeApiSteps;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesManager")
public class FavoritesManagerTests extends TestBase {
    FavoritesPage favoritesPage = new FavoritesPage();
    String phoneNumber = GenerationData.setRandomUserPhone();

    @BeforeEach
    void beforeEach() {
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle();
    }

    @Test
    @DisplayName("Проверить наличие на странице Мое избранное блока 'Ваш персональный менеджер'")
    void checkFavoritesManagerBlock () {
        favoritesPage.checkFavoritesManagerBlock();
    }

    @Test
    @DisplayName("Проверить имя менеджера в блоке 'Ваш персональный менеджер'")
    void checkFavoritesManagerName () {
        String managerNameExpected = MeApiSteps.getUserManagerName(phoneNumber);
        String managerNameActual = favoritesPage.getFavoritesManagerName();
        Assertions.assertEquals(managerNameExpected, managerNameActual);
    }

    @Test
    @DisplayName("Проверить номер телефона менеджера в блоке 'Ваш персональный менеджер'")
    void checkFavoritesManagerPhone () {
        String managerPhoneExpected = MeApiSteps.getUserManagerPhone(phoneNumber);
        String managerPhoneActual = favoritesPage.getFavoritesManagerPhone();
        Assertions.assertEquals(managerPhoneExpected, managerPhoneActual);
    }

    @Test
    @DisplayName("Проверить переход  в новое окно при клике на кнопку 'Написать в WhatsApp' в блоке 'Ваш персональный менеджер'")
    void checkFavoritesManagerChatText () {
        String textExpected = "https://api.whatsapp.com/send?phone=";
        favoritesPage
                .clickFavoritesManagerChatText()
                .checkUrlAfterClickFavoritesManagerChatText(textExpected);
    }

}
