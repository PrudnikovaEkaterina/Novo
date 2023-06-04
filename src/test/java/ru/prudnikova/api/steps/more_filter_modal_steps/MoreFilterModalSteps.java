package ru.prudnikova.api.steps.more_filter_modal_steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.prudnikova.api.models.building.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.prudnikova.api.specifications.Specification.requestSpec;
import static ru.prudnikova.api.specifications.Specification.responseSpec200;

public class MoreFilterModalSteps {

    @Step("В /api/buildings/ применить фильтр Площадь до и проверить, что в каждом найденном ЖК square_m2_from меньше, чем squareMax")
    public static void getBuildingListWithFilterSquareMax(float squareMax) {
        given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("square_max", squareMax)
                .get()
                .then()
                .spec(responseSpec200)
                .body("data.flats.square_m2.from", everyItem(lessThan(squareMax)));
    }

    @Step("В /api/buildings/ применить фильтр Площадь от и проверить, что в каждом найденном ЖК square_m2_to больше, чем squareMin ")
    public static void getBuildingListWithFilterSquareMin(double squareMin) {
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("square_min", squareMin)
                .get();
        assertEquals(200, response.getStatusCode());
        List<Object> listObject = response.path("data.flats.square_m2.to");
        List<String> listString = new ArrayList<>();
        for (int i = 0; i < listObject.size(); i++) {
            listString.add(String.valueOf(listObject.get(i)));
        }
        List<Double> listDouble = new ArrayList<>();
        for (int i = 0; i < listString.size(); i++) {
            listDouble.add(Double.parseDouble(listString.get(i)));
        }
        listDouble.forEach(num -> assertTrue(num > squareMin));
    }

}
