package ru.prudnikova.api.steps.auth;

import io.qameta.allure.Step;
import io.restassured.http.Cookie;
import org.aeonbits.owner.ConfigCache;
import ru.prudnikova.api.models.auth.AuthModel;
import ru.prudnikova.api.models.auth.UserLoginBodyModel;
import ru.prudnikova.config.AuthConfig;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static ru.prudnikova.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.prudnikova.api.specifications.Specification.*;

public class AuthSteps {

    static AuthConfig authConfig = ConfigCache.getOrCreate(AuthConfig.class);

    @Step("Авторизация пользователя")
    public static AuthModel auth(String phone) {
        String password = authConfig.smsCode();
        String authCookieName = authConfig.authCookieName();
        String authCookieValue = authConfig.authCookieValue();
        UserLoginBodyModel userLoginBodyModel = new UserLoginBodyModel();
        userLoginBodyModel.setPhone(phone);
        Cookie authCookie = new Cookie.Builder(authCookieName, authCookieValue).build();

        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .cookie(authCookie)
                .body(userLoginBodyModel)
                .when()
                .post("/api/auth/register")
                .then()
                .spec(responseSpec204);

        userLoginBodyModel.setPassword(password);
        return given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .cookie(authCookie)
                .body(userLoginBodyModel)
                .when()
                .post("/api/auth/login")
                .then()
                .spec(responseSpec200)
                .body("user.phone", is(phone))
                .extract().as(AuthModel.class);

    }

    @Step("Получение access token пользователя")
    public static String getAccessToken(String phone) {
        AuthModel authModel = auth(phone);
        return authModel.getAccessToken();
    }

    @Step("Получение refresh token пользователя")
    public static String getRefreshToken(String phone) {
        String password = authConfig.smsCode();
        String authCookieName = authConfig.authCookieName();
        String authCookieValue = authConfig.authCookieValue();
        UserLoginBodyModel userLoginBodyModel = new UserLoginBodyModel();
        userLoginBodyModel.setPhone(phone);
        Cookie authCookie = new Cookie.Builder(authCookieName, authCookieValue).build();

        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .cookie(authCookie)
                .body(userLoginBodyModel)
                .when()
                .post("/api/auth/register")
                .then()
                .spec(responseSpec204);

        userLoginBodyModel.setPassword(password);
        return given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .cookie(authCookie)
                .body(userLoginBodyModel)
                .when()
                .post("/api/auth/login")
                .then()
                .spec(responseSpec200)
                .body("user.phone", is(phone))
                .extract().cookie("refresh_token");
    }

    @Step("Логаут пользователя")
    public static void logout(String accessToken) {
        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/api/auth/logout")
                .then()
                .spec(responseSpec200)
                .body("message", is("Successfully logged out"));
    }

}
