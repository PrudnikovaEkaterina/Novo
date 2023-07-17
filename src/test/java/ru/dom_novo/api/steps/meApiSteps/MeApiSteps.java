package ru.dom_novo.api.steps.meApiSteps;

import io.qameta.allure.Step;
import ru.dom_novo.regexp.RegexpMeth;
import ru.dom_novo.api.models.authModels.UserModel;
import ru.dom_novo.api.steps.authApiSteps.AuthApi;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class MeApiSteps {

    private  static final String defaultManagerName = "Зверева Виктория";
    private  static final String defaultManagerPhone = "79605800786";

    @Step("Получить данные пользователя")
    public static UserModel getAuthMe (String phoneNumber) {
        String token = AuthApi.getAccessToken(phoneNumber);
        return given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/auth/me")
                .then()
                .spec(responseSpec200)
                .extract().as(UserModel.class);
    }
    @Step("Получить число избранных и рекомендованных объектов пользователя")
    public static int getFavoritesCounterForAuthUser (String phoneNumber) {
        UserModel user = getAuthMe(phoneNumber);
        return user.getFavoritesBuildingsCount() + user.getFavoritesFlatsCount()
                + user.getRecommendationsBuildingsCount() + user.getRecommendationsFlatsCount();
    }

    @Step("Получить имя пользователя с учетом форматирования для header")
    public static String getUserName (String phoneNumber) {
        UserModel user = getAuthMe(phoneNumber);
        return RegexpMeth.substring(10, user.getName()).split(" ")[0];
    }

    @Step("Получить id пользователя")
    public static int getUserId (String phoneNumber) {
        UserModel user = getAuthMe(phoneNumber);
        return user.getId();
    }

    @Step("Получить имя персонального менеджера пользователя")
    public static String getUserManagerName (String phoneNumber) {
        UserModel user = getAuthMe(phoneNumber);
        if (user.getManager()!=null)
            return user.getManager().getName();
        else
            return null;
    }


    @Step("Получить имя дефолтного менеджера")
    public static String getDefaultManagerName () {
        return defaultManagerName;
    }

    @Step("Получить номер телефона персонального менеджера пользователя")
    public static String getUserManagerPhone (String phoneNumber) {
        UserModel user = getAuthMe(phoneNumber);
        if (user.getManager()!=null)
            return user.getManager().getPhone();
        else return null;
    }

    @Step("Получить номер телефона дефолтного менеджера")
    public static String getDefaultManagerPhone () {
        return defaultManagerPhone;
    }

    @Step("Получить количество избранных ЖК пользователя")
    public static int getFavoritesBuildingsCount (String phoneNumber) {
        UserModel user = getAuthMe(phoneNumber);
        return user.getFavoritesBuildingsCount();
    }

    @Step("Получить количество избранных квартир пользователя")
    public static int getFavoritesFlatsCount (String phoneNumber) {
        UserModel user = getAuthMe(phoneNumber);
        return user.getFavoritesFlatsCount();
    }

    @Step("Получить общее количество рекомендованных пользователю объектов")
    public static int getRecommendationsCount (String phoneNumber) {
        UserModel user = getAuthMe(phoneNumber);
        return user.getRecommendationsFlatsCount()+user.getRecommendationsBuildingsCount();
    }

}
