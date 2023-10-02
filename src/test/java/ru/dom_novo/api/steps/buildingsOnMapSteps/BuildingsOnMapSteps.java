package ru.dom_novo.api.steps.buildingsOnMapSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.buildingModels.buildingOnMap.BuildingOnMapModel;
import ru.dom_novo.api.models.buildingModels.buildingOnMap.RootBuildingOnMapModel;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class BuildingsOnMapSteps {

    @Step("Получить список избранных ЖК с роута /api/buildings/list_on_map/")
    public static RootBuildingOnMapModel getRootBuildingOnMapModel(String phoneNumber) {
        String accessToken = AuthApiSteps.getAccessToken(phoneNumber);
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/list_on_map/")
                .header("Authorization", "Bearer " + accessToken)
                .param("no_flats", 1)
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("favorites", 1)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(RootBuildingOnMapModel.class);
    }


    @Step("Получить Map, где ключ - minPrice, а значение - buildingId из RootBuildingOnMapModel")
    public static Map<Integer, Integer> createMapFromRootBuildingOnMapModel(String phoneNumber) {
        return getRootBuildingOnMapModel(phoneNumber).getBuildingOnMapModelsList().stream()
                .collect(Collectors.toMap(BuildingOnMapModel::getMinPrice, BuildingOnMapModel::getId));
    }


    @Step("Отсортировать Map по ключу в порядке возрастания и вернуть список значений")
    public static List<Integer> sortMapByKeyAscAndReturnValue(Map<Integer, Integer> map) {
        return map.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Step("Отсортировать Map по ключу в порядке убывания и вернуть список id ЖК")
    public static List<Integer> sortMapByKeyDescAndReturnBuildingId(Map<Integer, Integer> map) {
        return map.entrySet().stream().sorted(Map.Entry.<Integer, Integer>comparingByKey().reversed()).map(Map.Entry::getValue).collect(Collectors.toList());
    }

}
