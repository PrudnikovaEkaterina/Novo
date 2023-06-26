package ru.prudnikova.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import regexp.RegexpMeth;
import ru.prudnikova.api.steps.cardNovostroykiApiSteps.CardNovostroykiApi;
import ru.prudnikova.dataBase.managers.BuildingDAO;
import ru.prudnikova.testData.GenerationData;
import ru.prudnikova.web.pages.NovostroykiPage;
import ru.prudnikova.web.tests.TestBase;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Tag("Web")
@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class SearchNovostroykiWithoutFlatsFromTrendAgent extends TestBase {

    NovostroykiPage novostroykiPage = new NovostroykiPage();

    @Test
    @DisplayName("Проверить значение цен в поиске ЖК по объектам без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkPriceValueForCardNovostroykiWithoutFlatsFromTrendAgent() throws IOException {
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlats();
        int buildingId = GenerationData.setRandomBuildingId(buildingIdList);
        double priceMin = CardNovostroykiApi.selectPriceMin(buildingId) / 1000000.0;
        if (priceMin != 0.0) {
            novostroykiPage.openNovostroykiPageWithFilterNoFlatsAndBuildingId(buildingId);
            Double searchPriceListValue = novostroykiPage.getPriceValue();
            Assertions.assertEquals(priceMin, searchPriceListValue);
        }
    }
}