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
@Story("FavoritesManager")
public class FavoritesManagerTests extends TestBase {
    FavoritesPage favoritesPage = new FavoritesPage();
    String phoneNumber = GenerationData.setRandomUserPhone();

    @BeforeEach
    void beforeEach() throws InterruptedException {
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
        String managerNameDefault = MeApiSteps.getDefaultManagerName();
        String managerNameActual = favoritesPage.getFavoritesManagerName();
        if (managerNameExpected!=null)
            assertThat(managerNameActual, is(managerNameExpected));
        else
            assertThat(managerNameActual, is(managerNameDefault));
    }

    @Test
    @DisplayName("Проверить номер телефона менеджера в блоке 'Ваш персональный менеджер'")
    void checkFavoritesManagerPhone () {
        String managerPhoneExpected = MeApiSteps.getUserManagerPhone(phoneNumber);
        String managerPhoneDefault = MeApiSteps.getDefaultManagerPhone();
        String managerPhoneActual = favoritesPage.getFavoritesManagerPhone();
        if (managerPhoneExpected!=null)
            assertThat(managerPhoneActual, is(managerPhoneExpected));
        else
            assertThat(managerPhoneActual, is(managerPhoneDefault));
    }

    @Test
    @DisplayName("Проверить переход  в новое окно при клике на кнопку 'Написать в WhatsApp' в блоке 'Ваш персональный менеджер'")
    void checkFavoritesManagerChatText () {
        String managerPhone =  MeApiSteps.getUserManagerPhone(phoneNumber);
        String novoPhone = "74951347236";
        String textExpected = "https://api.whatsapp.com/send?phone=";
        favoritesPage.clickFavoritesManagerChatText();
        System.out.println(textExpected+managerPhone);
        System.out.println(textExpected+novoPhone);
        if (managerPhone!=null)
            favoritesPage.checkUrlAfterClickFavoritesManagerChatText(textExpected +managerPhone);
        else
            favoritesPage.checkUrlAfterClickFavoritesManagerChatText(textExpected +novoPhone);
    }

}
