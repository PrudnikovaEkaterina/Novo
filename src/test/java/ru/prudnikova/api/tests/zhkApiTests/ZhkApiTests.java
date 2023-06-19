package ru.prudnikova.api.tests.zhkApiTests;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.prudnikova.api.enumsApi.BuildingEnum;
import ru.prudnikova.api.models.buildingDto.DataBuildingDto;
import ru.prudnikova.api.steps.zhkApiSteps.ZhkApi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Owner("PrudnikovaEkaterina")
@Tag("Api")
public class ZhkApiTests {

    @EnumSource(BuildingEnum.class)
    @ParameterizedTest(name = "Проверить, что список похожих ЖК для ЖК {0} учитывает логику по цене и сроку сдачи")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-549")
    @Comment("Тест падает для ЖК 17440, 9232 -  комментарий в задаче. Возможно, стоит переделать тест, чтобы сроки сдачи похожих ЖК" +
            "брались не из АПИ, а из базы")
    void checkSimilarBuildingList(BuildingEnum buildingEnum) throws IOException {
        int buildingId = buildingEnum.id;
        DataBuildingDto dataBuilding = ZhkApi.getSimilarBuildingList(buildingId);
        int priceFrom = ZhkApi.getPriceFrom(buildingId);
        List<Integer> pricesFromSimilarBuildingsList = ZhkApi.getPricesFromSimilarBuildings(dataBuilding);
        List<Long> calculatePercentageDifferenceBetweenPricesList = ZhkApi.calculatePercentageDifferenceBetweenPrices(priceFrom, pricesFromSimilarBuildingsList);
        ZhkApi.checkPercentageDifferenceLessOrEqual30(30, calculatePercentageDifferenceBetweenPricesList);
        int buildingReleaseYear = ZhkApi.selectBuildingReleaseYear(buildingId);
        Map<Integer, List<Integer>> releasesDatesFromBuildingsList = ZhkApi.getReleasesDatesFromBuildingsList(dataBuilding);
        ZhkApi.checkDifferenceBuildingReleaseYearLessOrEqual2Years(buildingReleaseYear, releasesDatesFromBuildingsList);
    }
}
