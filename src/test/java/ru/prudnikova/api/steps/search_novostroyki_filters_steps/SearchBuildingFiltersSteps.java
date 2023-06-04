package ru.prudnikova.api.steps.search_novostroyki_filters_steps;

import io.qameta.allure.Step;
import ru.prudnikova.api.models.building.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.prudnikova.api.specifications.Specification.requestSpec;
import static ru.prudnikova.api.specifications.Specification.responseSpec200;


public class SearchBuildingFiltersSteps {
    @Step("В /api/buildings/ применить фильтр Станция метро ={station} и получить список найденных ЖК")
    public static DataBuilding getBuildingListWithFilterStation(int stationId) {
       return given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("stations[]", stationId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(DataBuilding.class);
    }

    @Step("Проверить, что каждый ЖК из списка содержит станцию метро с id = {stationId}")
    public static void checkBuildingListContainsStationId(DataBuilding dataBuilding, int stationId) {
        List<Near> nearList = dataBuilding.getData().stream().map(Building::getNear).collect(Collectors.toList());
        for (Near near : nearList) {
            List<Station> stations = new ArrayList<>(near.getStations());
            List<Integer> listStationId = stations.stream().map(Station::getId).collect(Collectors.toList());
            for (int y = 0; y < listStationId.size(); y++) {
                assert listStationId.contains(stationId);
            }
        }
    }

    @Step("В /api/buildings/ применить фильтр Шоссе = {roadId} и получить список найденных ЖК")
    public static DataBuilding getBuildingListWithFilterRoads(int roadId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("roads[]", roadId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(DataBuilding.class);
    }

    @Step("Проверить, что каждый ЖК из списка содержит шоссе с id = {roadId}")
    public static void checkBuildingListContainsRoadId(DataBuilding dataBuilding, int roadId) {
        List<Near> nearList = dataBuilding.getData().stream().map(Building::getNear).collect(Collectors.toList());
        for (Near near : nearList) {
            List<Road> roadList = new ArrayList<>(near.getRoads());
            List<Integer> listRoadId = roadList.stream().map(Road::getId).collect(Collectors.toList());
            for (int y = 0; y < listRoadId.size(); y++) {
                assert listRoadId.contains(roadId);
            }
        }
    }

}
