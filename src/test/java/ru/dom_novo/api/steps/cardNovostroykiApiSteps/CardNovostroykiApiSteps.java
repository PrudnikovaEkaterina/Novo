package ru.dom_novo.api.steps.cardNovostroykiApiSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.buildingModels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class CardNovostroykiApiSteps {
    @Step("Получить информацию о ЖК")
    public static RootModel getBuildingData(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
//                .spec(responseSpec200)
                .extract().as(RootModel.class);
    }

    @Step("Получить минимальную цену ЖК")
    public static int getPriceFrom(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.flats.price.from");
    }

    @Step("Получить год сдачи ЖК")
    public static int getReleaseYear(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.release.year");
    }

    @Step("Получить список station_id")
    public static List<Integer> getStationIdList (int buildingId) {
        RootModel data = getBuildingData(buildingId);
        List<Integer> list = new ArrayList<>();
        if (data.getData().getNear().getStations() != null) {
           list = data.getData().getNear().getStations().stream().map(StationModel::getId).collect(Collectors.toList());
        }
        return list;
    }
    @Step("Получить значение district")
    public static int getDistrict(int buildingId) {
        RootModel data = getBuildingData(buildingId);
        int district=0;
        if (data.getData().getLocation().getDistrict()!=null){
            district = Integer.parseInt(data.getData().getLocation().getDistrict());}
        return district;
    }

    @Step("Получить список road_id")
    public static List<Integer> getRoadIdList (int buildingId) {
        RootModel data = getBuildingData(buildingId);
        List<Integer> list = new ArrayList<>();
        if (data.getData().getNear().getRoads() != null) {
            list = data.getData().getNear().getRoads().stream().map(RoadModel::getId).collect(Collectors.toList());
        }
        return list;
    }

}
