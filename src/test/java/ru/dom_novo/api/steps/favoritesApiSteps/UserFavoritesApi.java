package ru.dom_novo.api.steps.favoritesApiSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.buildingDto.BuildingDto;
import ru.dom_novo.api.models.buildingDto.BuildingDataDto;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class UserFavoritesApi {
    @Step("Получить список избранных ЖК пользователя")
    public static List<Integer> getUserFavoritesBuilding(String accessToken) {
        BuildingDataDto dataBuilding = given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/me/favorites/buildings/")
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class);
        return dataBuilding.getData().stream().map(BuildingDto::getId).collect(Collectors.toList());
    }
}
