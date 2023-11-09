package ru.dom_novo.api.steps.geoHomeSteps;

import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class GeoHomeSteps {
    @Step("Получить данные из роута /api/geo/home/subway")
    public static List<Integer> getSubwayId () {
        return given()
                .spec(requestSpec)
                .basePath("/api/geo/home/subway")
                .get()
                .then()
                .spec(responseSpec200)
                .extract()
                .path("data.id");
    }

    @Step("Получить данные из роута /api/geo/home/districts")
    public static List<Integer> getDistrictId () {
        return given()
                .spec(requestSpec)
                .basePath("/api/geo/home/districts")
                .get()
                .then()
                .spec(responseSpec200)
                .extract()
                .path("data.id");
    }

    @Step("Получить данные из роута /api/geo/home/cities")
    public static List<Integer> getCitiesId () {
        return given()
                .spec(requestSpec)
                .basePath("/api/geo/home/cities")
                .get()
                .then()
                .spec(responseSpec200)
                .extract()
                .path("data.id");
    }
}
