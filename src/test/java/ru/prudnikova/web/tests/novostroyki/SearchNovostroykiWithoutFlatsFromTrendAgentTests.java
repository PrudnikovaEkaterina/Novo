package ru.prudnikova.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.decimal4j.util.DoubleRounder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.steps.cardNovostroykiApiSteps.CardNovostroykiApi;
import ru.prudnikova.dataBase.managers.BuildingDAO;
import ru.prudnikova.testData.GenerationData;
import ru.prudnikova.web.pages.NovostroykiPage;
import ru.prudnikova.web.tests.TestBase;

import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.math.RoundingMode.DOWN;

@Tag("Web")
@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class SearchNovostroykiWithoutFlatsFromTrendAgentTests extends TestBase {

    NovostroykiPage novostroykiPage = new NovostroykiPage();
    String room;
    String pricesTitle;

    @Test
    @DisplayName("Проверить значение Цена Oт в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemTotalPriceForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        pricesTitle = "Продажа";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlats();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double priceMin = CardNovostroykiApi.selectPriceMin(buildingId,pricesTitle)/1000000.0;
        assert priceMin!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double searchPriceValue = novostroykiPage.getPriceValue();
        if (String.valueOf(priceMin).split("\\.")[1].length()>1) {
            double priceMinDoubleRound = DoubleRounder.round(priceMin, 1, DOWN);
            novostroykiPage.checkPriceValue(priceMinDoubleRound, searchPriceValue);
        }
            else
                novostroykiPage.checkPriceValue(priceMin, searchPriceValue);
        novostroykiPage.checkSearchPriceListRoomForBuildingWithoutFlatsFromTrendAgent();
    }

    @Test
    @DisplayName("Проверить значение Цена от за м2 в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemTotalPricePerSquareForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesExistAreaMin();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        int unitPriceMin = CardNovostroykiApi.selectUnitPriceMin(buildingId);
        assert unitPriceMin!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        int pricePerSquareValue = novostroykiPage.getPricePerSquareValue();
        novostroykiPage.checkPriceValue(unitPriceMin, pricePerSquareValue);
    }

    @Test
    @DisplayName("Проверить значение Цена Oт для Студий в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemStudioPriceForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        room ="Студии";
        pricesTitle = "Студии";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugExistStudio();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double priceMin = CardNovostroykiApi.selectPriceMin(buildingId, pricesTitle)/1000000.0;
        assert priceMin!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double searchStudioPriceValue = novostroykiPage.getStudioPriceValue(room);
        if (String.valueOf(priceMin).split("\\.")[1].length()>1) {
            double priceMinDoubleRound = DoubleRounder.round(priceMin, 1, DOWN);
                novostroykiPage.checkPriceValue(priceMinDoubleRound, searchStudioPriceValue);
        }
        else
                novostroykiPage.checkPriceValue(priceMin, searchStudioPriceValue);
    }

    @Test
    @DisplayName("Проверить значение Площадь от для Студий в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemStudioAreaMinForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        room ="Студии";
        pricesTitle = "Студии";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugExistStudio();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double areaMinApi = CardNovostroykiApi.selectAreaMin(buildingId, pricesTitle);
        assert areaMinApi!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double areaMinWeb = novostroykiPage.getStudioAreaValue(room);
        if (String.valueOf(areaMinApi).split("\\.")[1].length()>1) {
            double areaMinWebRound = DoubleRounder.round(areaMinWeb, 1, DOWN);
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWebRound);
        }
        else
                novostroykiPage.checkPriceValue(areaMinApi, areaMinWeb);
    }

    @Test
    @DisplayName("Проверить значение Цена от для 1-комн. квартир в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemRooms_1PriceForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        room ="1-комн.";
        pricesTitle = "1-комнатные";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugExistRooms_1();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double priceMin = CardNovostroykiApi.selectPriceMin(buildingId, pricesTitle)/1000000.0;
        assert priceMin!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double searchStudioPriceValue = novostroykiPage.getStudioPriceValue(room);
        if (String.valueOf(priceMin).split("\\.")[1].length()>1) {
            double priceMinDoubleRound = DoubleRounder.round(priceMin, 1, DOWN);
            novostroykiPage.checkPriceValue(priceMinDoubleRound, searchStudioPriceValue);
        }
        else
            novostroykiPage.checkPriceValue(priceMin, searchStudioPriceValue);
    }

    @Test
    @DisplayName("Проверить значение Площадь от для 1-комн. квартир в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemRooms_1AreaMinForBuildingWithoutFlatsFromTrendAgent() throws IOException {
       room ="1-комн.";
       pricesTitle = "1-комнатные";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugExistRooms_1();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double areaMinApi = CardNovostroykiApi.selectAreaMin(buildingId, pricesTitle);
        assert areaMinApi!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double areaMinWeb = novostroykiPage.getStudioAreaValue(room);
        if (String.valueOf(areaMinApi).split("\\.")[1].length()>1) {
            double areaMinWebRound = DoubleRounder.round(areaMinWeb, 1, DOWN);
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWebRound);
        }
        else
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWeb);
    }

    @Test
    @DisplayName("Проверить значение Цена от для 2-комн. квартир в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemRooms_2PriceForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        room ="2-комн.";
        pricesTitle = "2-комнатные";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugExistRooms_2();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double priceMin = CardNovostroykiApi.selectPriceMin(buildingId, pricesTitle)/1000000.0;
        assert priceMin!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double searchStudioPriceValue = novostroykiPage.getStudioPriceValue(room);
        if (String.valueOf(priceMin).split("\\.")[1].length()>1) {
            double priceMinDoubleRound = DoubleRounder.round(priceMin, 1, DOWN);
            novostroykiPage.checkPriceValue(priceMinDoubleRound, searchStudioPriceValue);
        }
        else
            novostroykiPage.checkPriceValue(priceMin, searchStudioPriceValue);
    }

    @Test
    @DisplayName("Проверить значение Площадь от для 2-комн. квартир в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemRooms_2AreaMinForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        room ="2-комн.";
        pricesTitle = "2-комнатные";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugExistRooms_2();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double areaMinApi = CardNovostroykiApi.selectAreaMin(buildingId, pricesTitle);
        assert areaMinApi!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double areaMinWeb = novostroykiPage.getStudioAreaValue(room);
        if (String.valueOf(areaMinApi).split("\\.")[1].length()>1) {
            double areaMinWebRound = DoubleRounder.round(areaMinWeb, 1, DOWN);
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWebRound);
        }
        else
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWeb);
    }
    @Test
    @DisplayName("Проверить значение Цена от для 3-комн. квартир в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemRooms_3PriceForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        room ="3-комн.";
        pricesTitle = "3-комнатные";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugExistRooms_3();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double priceMin = CardNovostroykiApi.selectPriceMin(buildingId, pricesTitle)/1000000.0;
        assert priceMin!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double searchStudioPriceValue = novostroykiPage.getStudioPriceValue(room);
        if (String.valueOf(priceMin).split("\\.")[1].length()>1) {
            double priceMinDoubleRound = DoubleRounder.round(priceMin, 1, DOWN);
            novostroykiPage.checkPriceValue(priceMinDoubleRound, searchStudioPriceValue);
        }
        else
            novostroykiPage.checkPriceValue(priceMin, searchStudioPriceValue);
    }

    @Test
    @DisplayName("Проверить значение Площадь от для 3-комн. квартир в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemRooms_3AreaMinForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        room ="3-комн.";
        pricesTitle = "3-комнатные";
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugExistRooms_3();
        assert buildingIdList != null;
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double areaMinApi = CardNovostroykiApi.selectAreaMin(buildingId, pricesTitle);
        assert areaMinApi!=0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        double areaMinWeb = novostroykiPage.getStudioAreaValue(room);
        if (String.valueOf(areaMinApi).split("\\.")[1].length()>1) {
            double areaMinWebRound = DoubleRounder.round(areaMinWeb, 1, DOWN);
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWebRound);
        }
        else
            novostroykiPage.checkPriceValue(areaMinApi, areaMinWeb);
    }
}