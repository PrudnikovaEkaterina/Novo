package ru.prudnikova.api.steps.favorites;

import io.qameta.allure.Step;
import ru.prudnikova.api.models.building.DataBuilding;

import static io.restassured.RestAssured.given;
import static ru.prudnikova.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.prudnikova.api.specifications.Specification.requestSpec;
import static ru.prudnikova.api.specifications.Specification.responseSpec200;

public class UserFavoritesSteps {
    @Step("Получить список избранных ЖК пользователя")
    public static DataBuilding getUserFavoritesBuilding (String accessToken) {
       return given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/me/favorites/buildings/")
                .then()
                .spec(responseSpec200)
                .extract().as(DataBuilding.class);

    }
}
