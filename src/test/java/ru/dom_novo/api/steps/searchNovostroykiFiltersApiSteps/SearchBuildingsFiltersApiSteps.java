package ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import ru.dom_novo.api.models.buildingModels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;


public class SearchBuildingsFiltersApiSteps {
    @Step("Получить список id ЖК с фильтром stations")
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

    public static List<BuildingDto> getBuildingListWithFilterApartments(int apartments) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("apartments", apartments)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class).getData();
    }

    @Step("Проверить, что каждый ЖК из списка содержит станцию метро с id = {stationId}")
    public static void checkBuildingListContainsStationId(BuildingDataDto dataBuilding, int stationId) {
        List<NearModel> nearList = dataBuilding.getData().stream().map(BuildingDto::getNear).collect(Collectors.toList());
        for (NearModel near : nearList) {
            List<StationModel> stations = new ArrayList<>(near.getStations());
            List<Integer> listStationId = stations.stream().map(StationModel::getId).collect(Collectors.toList());
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
        List<NearModel> nearList = dataBuilding.getData().stream().map(BuildingDto::getNear).collect(Collectors.toList());
        for (NearModel near : nearList) {
            List<RoadModel> roadList = new ArrayList<>(near.getRoads());
            List<Integer> listRoadId = roadList.stream().map(RoadModel::getId).collect(Collectors.toList());
            for (int y = 0; y < listRoadId.size(); y++) {
                assert listRoadId.contains(roadId);
            }
        }
    }

    @Step("Получить список ЖК c гет параметром no_flats = {noFlats} и фильтром Комнатность = {}")
    public static List<Integer> getBuildingIdListWithParameterNoFlatsAndFilterRoom(int noFlats, String room) {
        List<Integer> listAll = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("no_flats", noFlats)
                .param("rooms[]", room)
                .param("per_page", 1)
                .get();
        Assertions.assertEquals(200, response.statusCode());
        int totalItem = response.path("meta.total");
        int pageCount = (int) Math.round((double) totalItem / 100);
        for (int i = 1; i <pageCount+2; i++) {
            List<Integer> listPage = given()
                    .spec(requestSpec)
                    .basePath("/api/buildings/")
                    .param("region_code[]", 50)
                    .param("region_code[]", 77)
                    .param("no_flats", noFlats)
                    .param("rooms[]", room)
                    .param("per_page", 100)
                    .param("page", i)
                    .get()
                    .then()
                    .extract().path("data.id");
            listAll.addAll(listPage);
        }
        return listAll;
    }

    @Step("Получить список ЖК c гет параметром no_flats = {noFlats}")
    public static List<Integer> getBuildingIdListWithParameterNoFlats(int noFlats) {
        List<Integer> listAll = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("no_flats", noFlats)
                .param("per_page", 1)
                .get();
        Assertions.assertEquals(200, response.statusCode());
        int totalItem = response.path("meta.total");
        int pageCount = (int) Math.round((double) totalItem / 100);
        for (int i = 1; i <pageCount+2; i++) {
            List<Integer> listPage = given()
                    .spec(requestSpec)
                    .basePath("/api/buildings/")
                    .param("region_code[]", 50)
                    .param("region_code[]", 77)
                    .param("no_flats", noFlats)
                    .param("per_page", 100)
                    .param("page", i)
                    .get()
                    .then()
                    .extract().path("data.id");
            listAll.addAll(listPage);
        }
        return listAll;
    }
    @Step("Получить список ЖК c гет параметром no_flats = {noFlats} и фильтром Цена от = {priceMin} и цена По {priceMax}")
    public static List<Integer> getBuildingIdListWithParameterNoFlatsAndFilterPrice(int noFlats, int priceMin, int priceMax) {
        List<Integer> listAll = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("no_flats", noFlats)
                .param("price_min", priceMin )
                .param("price_max", priceMax )
                .param("per_page", 1)
                .get();
        Assertions.assertEquals(200, response.statusCode());
        int totalItem = response.path("meta.total");
        int pageCount = (int) Math.round((double) totalItem / 100);
        for (int i = 1; i <pageCount+2; i++) {
            List<Integer> listPage = given()
                    .spec(requestSpec)
                    .basePath("/api/buildings/")
                    .param("region_code[]", 50)
                    .param("region_code[]", 77)
                    .param("no_flats", noFlats)
                    .param("price_min", priceMin )
                    .param("price_max", priceMax )
                    .param("per_page", 100)
                    .param("page", i)
                    .get()
                    .then()
                    .extract().path("data.id");
            listAll.addAll(listPage);
        }
        return listAll;
    }

    @Step("Получить список ЖК c гет параметром no_flats = {noFlats} и фильтром Площадь от = {priceMin} и Площадь До {priceMax}")
    public static List<Integer> getBuildingIdListWithParameterNoFlatsAndFilterSquare(int noFlats, int squareMin, int squareMax) {
        List<Integer> listAll = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("no_flats", noFlats)
                .param("square_min", squareMin )
                .param("square_max", squareMax )
                .param("per_page", 1)
                .get();
        Assertions.assertEquals(200, response.statusCode());
        int totalItem = response.path("meta.total");
        int pageCount = (int) Math.round((double) totalItem / 100);
        for (int i = 1; i <pageCount+2; i++) {
            List<Integer> listPage = given()
                    .spec(requestSpec)
                    .basePath("/api/buildings/")
                    .param("region_code[]", 50)
                    .param("region_code[]", 77)
                    .param("no_flats", noFlats)
                    .param("square_min", squareMin )
                    .param("square_max", squareMax )
                    .param("per_page", 100)
                    .param("page", i)
                    .get()
                    .then()
                    .extract().path("data.id");
            listAll.addAll(listPage);
        }
        return listAll;
    }

    @Step("Получить список ЖК c гет параметром no_flats = {noFlats} и фильтром Этаж от = {floorMin} и Этаж По {floorMax}")
    public static List<Integer> getBuildingIdListWithParameterNoFlatsAndFilterFloor(int noFlats, int floorMin, int floorMax) {
        List<Integer> listAll = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("no_flats", noFlats)
                .param("floor_min", floorMin )
                .param("floor_max", floorMax )
                .param("per_page", 1)
                .get();
        Assertions.assertEquals(200, response.statusCode());
        int totalItem = response.path("meta.total");
        int pageCount = (int) Math.round((double) totalItem / 100);
        for (int i = 1; i <pageCount+2; i++) {
            List<Integer> listPage = given()
                    .spec(requestSpec)
                    .basePath("/api/buildings/")
                    .param("region_code[]", 50)
                    .param("region_code[]", 77)
                    .param("no_flats", noFlats)
                    .param("floor_min", floorMin )
                    .param("floor_max", floorMax )
                    .param("per_page", 100)
                    .param("page", i)
                    .get()
                    .then()
                    .extract().path("data.id");
            listAll.addAll(listPage);
        }
        return listAll;
    }

}
