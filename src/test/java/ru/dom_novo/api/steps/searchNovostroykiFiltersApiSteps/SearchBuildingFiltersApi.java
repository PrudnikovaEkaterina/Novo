package ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.buildingDto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;


public class SearchBuildingFiltersApi {
    @Step("Получить список ЖК с фильтром Станция метро = {station}")
    public static BuildingDataDto getBuildingListWithFilterStation(int stationId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("stations[]", stationId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class);
    }

    @Step("Проверить, что каждый ЖК из списка содержит станцию метро с id = {stationId}")
    public static void checkBuildingListContainsStationId(BuildingDataDto dataBuilding, int stationId) {
        List<NearDto> nearList = dataBuilding.getData().stream().map(BuildingDto::getNear).collect(Collectors.toList());
        for (NearDto near : nearList) {
            List<StationDto> stations = new ArrayList<>(near.getStations());
            List<Integer> listStationId = stations.stream().map(StationDto::getId).collect(Collectors.toList());
            for (int y = 0; y < listStationId.size(); y++) {
                assert listStationId.contains(stationId);
            }
        }
    }

    @Step("Получить список ЖК с фильтром Шоссе = {roadId}")
    public static BuildingDataDto getBuildingListWithFilterRoads(int roadId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("roads[]", roadId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class);
    }

    @Step("Проверить, что каждый ЖК из списка содержит шоссе с id = {roadId}")
    public static void checkBuildingListContainsRoadId(BuildingDataDto dataBuilding, int roadId) {
        List<NearDto> nearList = dataBuilding.getData().stream().map(BuildingDto::getNear).collect(Collectors.toList());
        for (NearDto near : nearList) {
            List<RoadDto> roadList = new ArrayList<>(near.getRoads());
            List<Integer> listRoadId = roadList.stream().map(RoadDto::getId).collect(Collectors.toList());
            for (int y = 0; y < listRoadId.size(); y++) {
                assert listRoadId.contains(roadId);
            }
        }
    }

}
