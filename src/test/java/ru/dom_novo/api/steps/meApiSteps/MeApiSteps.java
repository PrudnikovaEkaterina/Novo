package ru.dom_novo.api.steps.meApiSteps;

import io.qameta.allure.Step;
import regexp.RegexpMeth;
import ru.dom_novo.api.models.authDto.UserDto;
import ru.dom_novo.api.steps.authApiSteps.AuthApi;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class MeApiSteps {
    @Step("Получить данные пользователя")
    public static UserDto getAuthMe (String phoneNumber) {
        String token = AuthApi.getAccessToken(phoneNumber);
        return given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/auth/me")
                .then()
                .spec(responseSpec200)
                .extract().as(UserDto.class);
    }
    @Step("Получить число избранных и рекомендованных объектов пользователя")
    public static int getFavoritesCounterForAuthUser (String phoneNumber) {
        UserDto user = getAuthMe(phoneNumber);
        return user.getFavoritesBuildingsCount() + user.getFavoritesFlatsCount()
                + user.getRecommendationsBuildingsCount() + user.getRecommendationsFlatsCount();
    }

    @Step("Получить имя пользователя с учетом форматирования для header")
    public static String getUserName (String phoneNumber) {
        UserDto user = getAuthMe(phoneNumber);
        return RegexpMeth.substring(10, user.getName()).split(" ")[0];
    }
}
