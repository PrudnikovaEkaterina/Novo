package ru.dom_novo.web.tests.header;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.CardNovostroykiPage;
import ru.dom_novo.web.pages.MePage;
import ru.dom_novo.web.pages.components.HeaderComponent;
import ru.dom_novo.web.tests.TestBase;

import static java.lang.Thread.sleep;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
public class HeaderLogoTests extends TestBase {
    HeaderComponent headerComponent =new HeaderComponent();
    CardNovostroykiPage cardNovostroykiPage =new CardNovostroykiPage();
    MePage mePage = new MePage();

    @Test
    @DisplayName("Переход c карточки ЖК на главную при клике на логотип в header")
    void checkGoToMainPageFromCardPageAfterClickHeaderLogo ()  {
       cardNovostroykiPage.openCard(GenerationData.setRandomBuildingId());
       headerComponent
               .clickHeaderLogo()
               .checkGoToMainPageAfterClickHeaderLogo();
    }

    @Test
    @DisplayName("Переход co страницы Профиля на главную при клике на логотип в header")
    void checkGoToMainPageFromMePageAfterClickHeaderLogo ()  {
        mePage.openMePageWithApiAuth(GenerationData.setRandomPhoneNumber());
        headerComponent
                .clickHeaderLogo()
                .checkGoToMainPageAfterClickHeaderLogo();
    }

}
