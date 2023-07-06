package ru.prudnikova.api.steps.cardNovostroykiApiSteps;

import io.qameta.allure.Step;
import ru.prudnikova.api.models.buildingDto.*;

import static io.restassured.RestAssured.given;

import static ru.prudnikova.api.specifications.Specification.requestSpec;
import static ru.prudnikova.api.specifications.Specification.responseSpec200;

public class CardNovostroykiApiSteps {
    @Step("Получить информацию о ЖК")
    public static RootDto getBuildingData(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(RootDto.class);
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

}
