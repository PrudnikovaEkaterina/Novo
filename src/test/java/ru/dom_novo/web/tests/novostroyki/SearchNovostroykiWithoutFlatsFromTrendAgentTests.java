package ru.dom_novo.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.decimal4j.util.DoubleRounder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.dom_novo.dataBase.services.BuildingService;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.tests.TestBase;

import java.io.IOException;

import static java.math.RoundingMode.DOWN;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
public class SearchNovostroykiWithoutFlatsFromTrendAgentTests extends TestBase {

    NovostroykiPage novostroykiPage = new NovostroykiPage();
    String pricesTitle = "Продажа";

    @Test
    @DisplayName("Проверить значение Цена Oт в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemTotalPriceForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        int buildingId = BuildingService.getBuildingIdWithoutFlatsWherePricesExistUnitPriceMin();
        double priceMin = BuildingService.selectPriceMin(buildingId, pricesTitle) / 1000000.0;
        assert priceMin != 0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double searchPriceValue = novostroykiPage.getPriceValue();
        if (String.valueOf(priceMin).split("\\.")[1].length() > 1) {
            double priceMinDoubleRound = DoubleRounder.round(priceMin, 1, DOWN);
            novostroykiPage.checkPriceValue(priceMinDoubleRound, searchPriceValue);
        } else
            novostroykiPage.checkPriceValue(priceMin, searchPriceValue);
        novostroykiPage.checkSearchPriceListRoomForBuildingWithoutFlatsFromTrendAgent();
    }

    @Test
    @DisplayName("Проверить значение Цена от за м2 в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemTotalPricePerSquareForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        int buildingId = BuildingService.getBuildingIdWithoutFlatsWherePricesExistAreaMin();
        int unitPriceMin = BuildingService.selectUnitPriceMin(buildingId, pricesTitle);
        assert unitPriceMin != 0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        int pricePerSquareValue = novostroykiPage.getPricePerSquareValue();
        novostroykiPage.checkPriceValue(unitPriceMin, pricePerSquareValue);
    }

    @CsvSource(value = {"%sell_nb_studio%, Студии, Студии",
            "%sell_nb_rooms_1%, 1-комн., 1-комнатные",
            "%sell_nb_rooms_2%, 2-комн., 2-комнатные",
            "%sell_nb_rooms_3%, 3-комн., 3-комнатные",
            "%sell_nb_rooms_4_plus%, 4 и более, 4-комнатные и более"})
    @ParameterizedTest(name = "Проверить значение Цена Oт для типа квартир {2} в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkItemRoomPriceMinForBuildingWithoutFlatsFromTrendAgent(String data1, String data2, String data3) throws IOException {
        int buildingId = BuildingService.getBuildingIdWithoutFlatsWherePricesSlugExistRoom(data1);
        double priceMin = BuildingService.selectPriceMin(buildingId, data3) / 1000000.0;
        assert priceMin != 0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double searchStudioPriceValue = novostroykiPage.getStudioPriceValue(data2);
        if (String.valueOf(priceMin).split("\\.")[1].length() > 1) {
            double priceMinDoubleRound = DoubleRounder.round(priceMin, 1, DOWN);
            novostroykiPage.checkPriceValue(priceMinDoubleRound, searchStudioPriceValue);
        } else
            novostroykiPage.checkPriceValue(priceMin, searchStudioPriceValue);
    }

    @CsvSource(value = {"%sell_nb_studio%, Студии, Студии",
            "%sell_nb_rooms_1%, 1-комн., 1-комнатные",
            "%sell_nb_rooms_2%, 2-комн., 2-комнатные",
            "%sell_nb_rooms_3%, 3-комн., 3-комнатные",
            "%sell_nb_rooms_4_plus%, 4 и более, 4-комнатные и более"})
    @ParameterizedTest(name = "Проверить значение Площадь от для типа квартир {2} в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemStudioAreaMinForBuildingWithoutFlatsFromTrendAgent(String data1, String data2, String data3) throws IOException {
        int buildingId = BuildingService.getBuildingIdWithoutFlatsWherePricesSlugExistRoom(data1);
        double areaMinApi = BuildingService.selectAreaMin(buildingId, data3);
        assert areaMinApi != 0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double areaMinWeb = novostroykiPage.getStudioAreaValue(data2);
        if (String.valueOf(areaMinApi).split("\\.")[1].length() > 1) {
            double areaMinWebRound = DoubleRounder.round(areaMinWeb, 1, DOWN);
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWebRound);
        } else
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWeb);
    }

}