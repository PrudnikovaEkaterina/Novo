package ru.dom_novo.api.tests.cardNovostroykiApiTests;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.dom_novo.api.enumsApi.BuildingEnum;
import ru.dom_novo.api.models.buildingModels.BuildingDataDto;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiSimilarApiSteps;
import ru.dom_novo.dataBase.dao.BuildingDao;

import java.util.List;
import java.util.Map;


@Owner("PrudnikovaEkaterina")
@Tag("Api")
@TmsLink("https://tracker.yandex.ru/NOVODEV-549")
public class CardNovostroykiSimilarApiTests {

    @EnumSource(BuildingEnum.class)
    @ParameterizedTest(name = "Проверить, что список похожих ЖК для ЖК {0} учитывает логику по цене")
    void checkPricesSimilarBuildingList(BuildingEnum buildingEnum) {
        int buildingId = buildingEnum.id;
        int priceFrom = CardNovostroykiApiSteps.getPriceFrom(buildingId);
        assert (priceFrom != 0);
        BuildingDataDto dataBuilding = CardNovostroykiSimilarApiSteps.getSimilarBuildingDataList(buildingId);
        List<Integer> pricesFromSimilarBuildingsList = CardNovostroykiSimilarApiSteps.getPricesFromSimilarBuildings(dataBuilding);
        List<Long> calculatePercentageDifferenceBetweenPricesList = CardNovostroykiSimilarApiSteps.calculatePercentageDifferenceBetweenPrices(priceFrom, pricesFromSimilarBuildingsList);
        CardNovostroykiSimilarApiSteps.checkPercentageDifferenceLessOrEqual30(30, calculatePercentageDifferenceBetweenPricesList);
    }

    @EnumSource(BuildingEnum.class)
    @ParameterizedTest(name = "Проверить, что список похожих ЖК для ЖК {0} учитывает логику по сроку сдачи")
    void checkReleaseYearSimilarBuildingList(BuildingEnum buildingEnum) {
        int buildingId = buildingEnum.id;
        int buildingReleaseYear = BuildingDao.selectBuildingReleaseYear(buildingId);
        assert (buildingReleaseYear != 0);
        BuildingDataDto dataBuilding = CardNovostroykiSimilarApiSteps.getSimilarBuildingDataList(buildingId);
        Map<Integer, List<Integer>> map = CardNovostroykiSimilarApiSteps.getMapKeyIsBuildingIdValuesIsHousingId(dataBuilding);
        CardNovostroykiSimilarApiSteps.checkDifferenceBuildingReleaseYearLessOrEqual2Years(buildingReleaseYear, map);
    }
}
