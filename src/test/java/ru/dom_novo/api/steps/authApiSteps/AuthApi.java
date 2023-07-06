package ru.dom_novo.api.steps.authApiSteps;

import io.qameta.allure.Step;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigCache;
import org.junit.jupiter.api.Assertions;
import ru.dom_novo.api.models.authDto.AuthDto;
import ru.dom_novo.api.models.authDto.UserLoginBodyDto;
import ru.dom_novo.config.AuthConfig;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static ru.dom_novo.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.dom_novo.api.specifications.Specification.*;

public class AuthApi {
    static AuthConfig authConfig = ConfigCache.getOrCreate(AuthConfig.class);
    static String password = authConfig.smsCode();
    static String authCookieName = authConfig.authCookieName();
    static String authCookieValue = authConfig.authCookieValue();
    static Cookie authCookie = new Cookie.Builder(authCookieName, authCookieValue).build();
    public static void authRegister(Cookie authCookie, UserLoginBodyDto userLoginBody ) {
        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .cookie(authCookie)
                .body(userLoginBody)
                .when()
                .post("/api/auth/register")
                .then()
                .spec(responseSpec204);
    }

    public static Response authLogin(Cookie authCookie, UserLoginBodyDto userLoginBody) {
        Response response =given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .cookie(authCookie)
                .body(userLoginBody)
                .when()
                .post("/api/auth/login");
        Assertions.assertEquals(200, response.getStatusCode());
        response.path("user.phone", String.valueOf(is(userLoginBody.getPhone())));
        return response;
    }
    public static UserLoginBodyDto setPhoneToUserLoginBody(String phone) {
        UserLoginBodyDto userLoginBody= new UserLoginBodyDto();
        userLoginBody.setPhone(phone);
        return userLoginBody;
    }
    @Step("Авторизация пользователя")
    public static AuthDto auth(String phone) {
        UserLoginBodyDto userLoginBody = setPhoneToUserLoginBody(phone);
        authRegister(authCookie, userLoginBody);
        userLoginBody.setPassword(password);
        Response response = authLogin(authCookie, userLoginBody);
        Assertions.assertNotNull(response.getBody().as(AuthDto.class).getAccessToken());
        Assertions.assertNotNull(response.getBody().as(AuthDto.class).getRefreshToken());
        Assertions.assertNotNull(response.getBody().as(AuthDto.class).getUser());
        return response.getBody().as(AuthDto.class);
    }

    @Step("Получение access token пользователя")
    public static String getAccessToken(String phone) {
        AuthDto authModel = auth(phone);
        return authModel.getAccessToken();
    }

    @Step("Получение refresh token пользователя")
    public static String getRefreshToken(String phone) {
        UserLoginBodyDto userLoginBody = setPhoneToUserLoginBody(phone);
        authRegister(authCookie, userLoginBody);
        userLoginBody.setPassword(password);
        Response response = authLogin(authCookie, userLoginBody);
        return response.getCookie("refresh_token");
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
