package ru.prudnikova.web.tests.cardNovostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.steps.cardNovostroykiApiSteps.CardNovostroykiApi;
import ru.prudnikova.dataBase.managers.BuildingDAO;
import ru.prudnikova.testData.GenerationData;
import ru.prudnikova.web.pages.CardNovostroykiPage;
import ru.prudnikova.web.tests.TestBase;

import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@Tag("Web")
@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class CardNovostroykiWithoutFlatsFromTrendAgentTests extends TestBase {
    CardNovostroykiPage cardNovostroykiPage = new CardNovostroykiPage();

    @Test
    @DisplayName("Проверить значение цен в карточке ЖК по объектам без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkPriceValueForCardNovostroykiWithoutFlatsFromTrendAgent() throws IOException {
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugContainsRoom();
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        int priceMin = CardNovostroykiApi.selectPriceMin(buildingId);
        if (priceMin != 0) {
            cardNovostroykiPage.openZhkPage(buildingId);
            int cardNovostroykiPriceValue = cardNovostroykiPage.getPriceValue();
            int cardNovostroykiProfilePriceValue = cardNovostroykiPage.getProfilePriceValue();
            Assertions.assertEquals(priceMin, cardNovostroykiPriceValue);
            Assertions.assertEquals(priceMin, cardNovostroykiProfilePriceValue);
        }
    }

    @Test
    @DisplayName("Проверить значение минимальной площади квартир в карточке ЖК по объектам без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkAreaMinValueForCardNovostroykiWithoutFlatsFromTrendAgent() throws IOException {
        open();
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesExistAreaMin();
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        if (buildingId != 0) {
            double areaMin = CardNovostroykiApi.selectAreaMin(buildingId);
            if (areaMin != 0) {
                cardNovostroykiPage.openZhkPage(buildingId);
                double cardValueAreaMin = cardNovostroykiPage.getCardValueAreaMin();
                Assertions.assertEquals(areaMin, cardValueAreaMin);
            }
        }
    }

    @Test
    @DisplayName("Проверить значение минимальной площади квартир в карточке ЖК по объектам без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkAreaMaxValueForCardNovostroykiWithoutFlatsFromTrendAgent() throws IOException {
        open();
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesExistAreaMax();
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        if (buildingId != 0) {
            double areaMax = CardNovostroykiApi.selectAreaMax(buildingId);
            if (areaMax != 0) {
                cardNovostroykiPage.openZhkPage(buildingId);
                double cardValueAreaMax = cardNovostroykiPage.getCardValueAreaMax();
                Assertions.assertEquals(areaMax, cardValueAreaMax);
            }
        }
    }

}


