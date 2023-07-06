package ru.prudnikova.web.tests.cardNovostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.dataBase.services.BuildingService;
import ru.prudnikova.web.pages.CardNovostroykiPage;
import ru.prudnikova.web.tests.TestBase;

import java.io.IOException;

@Tag("Web")
@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class CardNovostroykiWithoutFlatsFromTrendAgentTests extends TestBase {
    CardNovostroykiPage cardNovostroykiPage = new CardNovostroykiPage();

    @Test
    @DisplayName("Проверить значение цен в карточке ЖК по объектам без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkPriceValueForCardNovostroykiWithoutFlatsFromTrendAgent() throws IOException {
        String title = "Продажа";
        int buildingId =BuildingService.getBuildingIdWithoutFlatsWherePricesExistUnitPriceMin();
        int priceMin = BuildingService.selectPriceMin(buildingId, title);
        if (priceMin != 0) {
            cardNovostroykiPage.openCard(buildingId);
            int cardNovostroykiPriceValue = cardNovostroykiPage.getPriceValue();
            int cardNovostroykiProfilePriceValue = cardNovostroykiPage.getProfilePriceValue();
            Assertions.assertEquals(priceMin, cardNovostroykiPriceValue);
            Assertions.assertEquals(priceMin, cardNovostroykiProfilePriceValue);
        }
    }

}


