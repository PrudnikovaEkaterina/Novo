package ru.dom_novo.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.decimal4j.util.DoubleRounder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.dataBase.services.BuildingService;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.tests.TestBase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import static java.math.RoundingMode.CEILING;
import static java.math.RoundingMode.DOWN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
public class SearchNovostroykiWithoutFlatsFromTrendAgentTests extends TestBase {

    NovostroykiPage novostroykiPage = new NovostroykiPage();
    String pricesTitle = "Продажа";
    List<String> roomList = List.of("Студии", "1-комн.", "2-комн.", "3-комн.", "4 и более");

    @Test
    @DisplayName("Проверить значение Цена Oт в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemTotalPriceForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        List<Integer> buildingIdList = BuildingDao.selectBuildingIdWithoutFlatsWherePricesExistUnitPriceMin();
        int buildingId = 3701;
                GenerationData.setRandomBuildingId(buildingIdList);
        System.out.println(buildingId);
        int priceFromExpected = CardNovostroykiApiSteps.getPriceFrom(buildingId);
        System.out.println(priceFromExpected);
        double pr = DoubleRounder.round((double) priceFromExpected/1000000, 1, DOWN);
        System.out.println(pr);


//        double priceMin = BuildingService.selectPriceMin(buildingId, pricesTitle) / 1000000.0;
//        assert priceMin != 0;
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
//        double searchPriceValue = novostroykiPage.getRoomPriceValue();
//        if (String.valueOf(priceMin).split("\\.")[1].length() > 1) {
//            double priceMinDoubleRound = DoubleRounder.round(priceMin, 1, DOWN);
//            novostroykiPage.checkPriceValue(priceMinDoubleRound, searchPriceValue);
//        } else
//            novostroykiPage.checkPriceValue(priceMin, searchPriceValue);
//        novostroykiPage.checkSearchPriceListRoomForBuildingWithoutFlatsFromTrendAgent();
    }
    // завтра рефаторю

    @Test
    @DisplayName("Проверить значение Цена от за м2 в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemTotalPricePerSquareForBuildingWithoutFlatsFromTrendAgent() throws IOException {
        List<Integer> buildingIdList = BuildingDao.selectBuildingIdWithoutFlatsWherePricesExistAreaMin();
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        int priceM2Expected = CardNovostroykiApiSteps.getPriceM2From(buildingId);
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        int priceM2Actual = novostroykiPage.getPricePerSquareValue();
        assertThat(priceM2Actual, is(priceM2Expected));
    }

    @Test
    @DisplayName("Проверить значение Цена Oт для каждого из типов квартир в поиске ЖК для объекта без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkItemRoomPriceMinForBuildingWithoutFlatsFromTrendAgent() {
        int buildingId = 16215;  //BuildingService.getBuildingIdWithoutFlatsWherePricesSlugExistRoom("%sell_nb_rooms_1%");
        List<Integer> flatsOffersPriceFromList = CardNovostroykiApiSteps.getFlatsOffersPriceFromList(buildingId);
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        for (int i =0; i<flatsOffersPriceFromList.size(); i++){
            double flatsOffersPriceFromValue = DoubleRounder.round(flatsOffersPriceFromList.get(i)/1000000.0, 1, DOWN);
            assertThat(flatsOffersPriceFromValue, is (novostroykiPage.getRoomPriceValue(roomList.get(i))));
        }
    }

    @Test
    @DisplayName("Проверить значение Площадь от для всех типов квартир в поиске для ЖК без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkSearchItemStudioAreaMinForBuildingWithoutFlatsFromTrendAgent() {
        int buildingId = 16215;  //BuildingService.getBuildingIdWithoutFlatsWherePricesSlugExistRoom("%sell_nb_rooms_1%");
        List<String> flatsOffersSquareM2FromList = CardNovostroykiApiSteps.getFlatsOffersSquareM2FromList(buildingId);
        novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
        for (int i =0; i<flatsOffersSquareM2FromList.size(); i++){
           String flatsOffersSquareM2FromValue = String.valueOf(flatsOffersSquareM2FromList.get(i));
            assertThat(flatsOffersSquareM2FromValue, is (novostroykiPage.getRoomAreaValue(roomList.get(i))));
        }
    }
}