package ru.prudnikova.api.steps.favoritesApiSteps;

import io.qameta.allure.Step;
import ru.prudnikova.api.models.buildingDto.BuildingDto;
import ru.prudnikova.api.models.buildingDto.BuildingDataDto;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.prudnikova.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.prudnikova.api.specifications.Specification.requestSpec;
import static ru.prudnikova.api.specifications.Specification.responseSpec200;

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
