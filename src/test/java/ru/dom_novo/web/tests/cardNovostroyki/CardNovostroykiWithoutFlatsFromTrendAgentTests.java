package ru.dom_novo.web.tests.cardNovostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.dataBase.services.BuildingService;
import ru.dom_novo.regexp.RegexpMeth;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.CardNovostroykiPage;
import ru.dom_novo.web.tests.TestBase;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
public class CardNovostroykiWithoutFlatsFromTrendAgentTests extends TestBase {
    CardNovostroykiPage cardNovostroykiPage = new CardNovostroykiPage();

    @Test
    @DisplayName("Проверить значение цен в карточке ЖК по объектам без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkPriceValueForCardNovostroykiWithoutFlatsFromTrendAgent() throws IOException {
        String title = "Продажа";
        List<Integer> buildingIdList = BuildingDao.selectBuildingIdWithoutFlatsWherePricesExistUnitPriceMin();
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
//        int buildingId =BuildingService.getBuildingIdWithoutFlatsWherePricesExistUnitPriceMin();
        int priceMin = BuildingService.selectPriceMin(buildingId, title);
        if (priceMin != 0) {
            cardNovostroykiPage.open(buildingId);
            String priceValue = cardNovostroykiPage.getPriceValue();
            int cardNovostroykiPriceValue = RegexpMeth.extractPrice(RegexpMeth.removeSpacesFromString(priceValue));
            String profilePriceValue = cardNovostroykiPage.getProfilePriceValue();
            int cardNovostroykiProfilePriceValue = RegexpMeth.extractPrice(RegexpMeth.removeSpacesFromString(profilePriceValue));
            assertThat(cardNovostroykiPriceValue, is(priceMin));
            assertThat(cardNovostroykiProfilePriceValue, is(priceMin));
        }
    }
}


