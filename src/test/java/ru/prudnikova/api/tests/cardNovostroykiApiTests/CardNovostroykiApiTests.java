package ru.prudnikova.api.tests.cardNovostroykiApiTests;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.prudnikova.api.enumsApi.BuildingEnum;
import ru.prudnikova.api.models.buildingDto.DataBuildingDto;
import ru.prudnikova.api.steps.cardNovostroykiApiSteps.CardNovostroykiApi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Owner("PrudnikovaEkaterina")
@Tag("Api")
public class CardNovostroykiApiTests {

    @EnumSource(BuildingEnum.class)
    @ParameterizedTest(name = "Проверить, что список похожих ЖК для ЖК {0} учитывает логику по цене и сроку сдачи")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-549")
    void checkSimilarBuildingList(BuildingEnum buildingEnum) throws IOException {
        int buildingId = buildingEnum.id;
        DataBuildingDto dataBuilding = CardNovostroykiApi.getSimilarBuildingList(buildingId);
        int priceFrom = CardNovostroykiApi.getPriceFrom(buildingId);
        List<Integer> pricesFromSimilarBuildingsList = CardNovostroykiApi.getPricesFromSimilarBuildings(dataBuilding);
        List<Long> calculatePercentageDifferenceBetweenPricesList = CardNovostroykiApi.calculatePercentageDifferenceBetweenPrices(priceFrom, pricesFromSimilarBuildingsList);
        CardNovostroykiApi.checkPercentageDifferenceLessOrEqual30(30, calculatePercentageDifferenceBetweenPricesList);
        int buildingReleaseYear = CardNovostroykiApi.selectBuildingReleaseYear(buildingId);
        Map<Integer, Integer> releasesDatesFromBuildingsList = CardNovostroykiApi.getReleasesDates(dataBuilding);
        CardNovostroykiApi.checkDifferenceBuildingReleaseYearLessOrEqual2Years(buildingReleaseYear, releasesDatesFromBuildingsList);
    }

}
