package ru.dom_novo.api.steps.cardNovostroykiApiSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.buildingModels.BuildingDataDto;
import ru.dom_novo.api.models.buildingModels.BuildingDto;
import ru.dom_novo.api.models.buildingModels.FlatModel;
import ru.dom_novo.api.models.buildingModels.PriceModel;
import ru.dom_novo.dataBase.services.BuildingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.toList;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class CardNovostroykiSimilarApiSteps {
    @Step("Получить список похожих ЖК")
    public static BuildingDataDto getSimilarBuildingDataList(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId + "/similar/")
                .param("per_page", 16)
                .param("page", 1)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class);
    }
    @Step("Получить id из списка похожих ЖК")
    public static List<Integer> getBuildingIdList(BuildingDataDto dataBuildingDto) {
        return dataBuildingDto.getData().stream().map(BuildingDto::getId).collect(toList());
    }

    @Step("Получить карту похожих ЖК, где ключ = id, а values = список id корпусов")
    public static Map<Integer, List<Integer>> getMapKeyIsBuildingIdValuesIsHousingId (BuildingDataDto dataBuildingDto) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> similarBuildingIdList = getBuildingIdList(dataBuildingDto);
        for (Integer integer : similarBuildingIdList) {
            map.put(integer, BuildingService.getBuildingIdFromBuildingsWhereParentIdIs(integer));
        }
        return map;
    }
    @Step("Проверить, что разница в сроках сдачи проверяемого ЖК и похожих ЖК с учетом корпусов - не более 2 лет")
    public static void checkDifferenceBuildingReleaseYearLessOrEqual2Years (int releaseYear, Map<Integer, List<Integer>> mapKeyIsBuildingIdValuesIsHousingId) {
        List<Integer> getDateRange = List.of(releaseYear - 2, releaseYear - 1, releaseYear, releaseYear + 1, releaseYear + 2);
        for (Map.Entry<Integer, List<Integer>> el: mapKeyIsBuildingIdValuesIsHousingId.entrySet()) {
            List<Integer> list=new ArrayList<>();
            for (int i=0; i<el.getValue().size(); i++){
//                list.add(BuildingDao.selectBuildingReleaseYear(el.getValue().get(i)));
                list.add(CardNovostroykiApiSteps.getReleaseYear(el.getValue().get(i)));
            }
//            list.add(BuildingDao.selectBuildingReleaseYear(el.getKey()));
            list.add(CardNovostroykiApiSteps.getReleaseYear(el.getKey()));
            for (int y=0; y<list.size(); y++){
                assert list.get(y) == 0 || list.stream().anyMatch(getDateRange::contains);
            }
        }
    }
//    (Устарело, для примера)
//     public static Map<Integer, List<Integer>> getReleasesDatesLegacy(DataBuildingDto dataBuildingDto){
//        Map<Integer, String> mapa = dataBuildingDto.getData().stream().collect(toMap(BuildingDto::getId, BuildingDto::getReleaseDate));
//        return mapa.entrySet()
//                .stream()
//                .collect(toMap(Entry::getKey, el -> RegexpMeth.extractYears(el.getValue())));}

    @Step("Получить минимальные цены для каждого объекта из списка похожих ЖК")
    public static List<Integer> getPricesFromSimilarBuildings(BuildingDataDto dataBuildingDto) {
        return dataBuildingDto.getData().stream().map(BuildingDto::getFlats).map(FlatModel::getPrice).map(PriceModel::getFrom).collect(toList());
    }

    @Step("Посчитать разницу в процентах между минимальной ценой ЖК и минимальными ценами похожих ЖК")
    public static List<Long> calculatePercentageDifferenceBetweenPrices(int priceFrom, List<Integer> pricesFrom) {
        List<Long> listPercentageDifferenceBetweenPrices = new ArrayList<>();
        for (Integer integer : pricesFrom) {
            if (priceFrom < integer) {
                listPercentageDifferenceBetweenPrices.add(Math.round((((double) (integer - priceFrom) / priceFrom) * 100)));
            } else
                listPercentageDifferenceBetweenPrices.add(Math.round((((double) (priceFrom - integer) / priceFrom) * 100)));

        }
        return listPercentageDifferenceBetweenPrices;
    }

    @Step("Проверить, что разница в процентах между минимальной ценой ЖК и минимальными ценами похожих ЖК составляет не более 30%")
    public static void checkPercentageDifferenceLessOrEqual30 (int percent, List<Long> calculatePercentageDifferenceBetweenPrices) {
        for (Long calculatePercentageDifferenceBetweenPrice : calculatePercentageDifferenceBetweenPrices) {
            assert calculatePercentageDifferenceBetweenPrice <= percent;
        }
    }
}
