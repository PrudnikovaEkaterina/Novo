package ru.dom_novo.api.steps.authApiSteps;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigCache;
import org.junit.jupiter.api.Assertions;
import ru.dom_novo.api.models.authModels.LoginResponseModel;
import ru.dom_novo.api.models.authModels.LoginRequestModel;
import ru.dom_novo.config.AuthConfig;

import java.sql.Timestamp;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
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

    public static void authRegister(Cookie authCookie, LoginRequestModel userLoginBody ) {
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

    public static Response authLogin(Cookie authCookie, LoginRequestModel userLoginBody) {
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
    public static LoginRequestModel setPhoneToLoginBody(String phone) {
        LoginRequestModel loginBody= new LoginRequestModel();
        loginBody.setPhone(phone);
        return loginBody;
    }

@Step("Авторизация пользователя")
public static void auth(String phone) {
    LoginRequestModel loginBody = setPhoneToLoginBody(phone);
    authRegister(authCookie, loginBody);
    loginBody.setPassword(password);
    Response response = authLogin(authCookie, loginBody);
    Assertions.assertNotNull(response.getBody().as(LoginResponseModel.class).getAccessToken());
    Assertions.assertNotNull(response.getBody().as(LoginResponseModel.class).getRefreshToken());
    Assertions.assertNotNull(response.getBody().as(LoginResponseModel.class).getUser());
}

    @Step("Установка авторизационных кук в браузер")
    public static void setAuthCookiesToBrowser(String phoneNumber) {
        Response loginResponse = getLoginResponse(phoneNumber);
        String refreshToken = getRefreshTokenFromLoginResponse(loginResponse);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long timestampTime = timestamp.getTime();
        long session = timestampTime + 2592000;
        String sessionExpiresAt = String.valueOf(session).substring(0, 10);
        String referralCode = getReferralCode(loginResponse);
        open("https://novo-dom.ru/build/desktop/images/header-logo.c8b95b60.svg");
        org.openqa.selenium.Cookie cookie = new org.openqa.selenium.Cookie("refresh_token", refreshToken);
        getWebDriver().manage().addCookie(cookie);
        if (referralCode!=null){
            org.openqa.selenium.Cookie cookie1 = new org.openqa.selenium.Cookie("ref", referralCode);
            getWebDriver().manage().addCookie(cookie1);
        }
        Selenide.localStorage().setItem("session_expires_at", sessionExpiresAt);
        Selenide.refresh();
    }

    @Step("Получение access token пользователя")
    public static String getAccessToken(String phone) {
        Response loginResponse = getLoginResponse(phone);
        return loginResponse.body().as(LoginResponseModel.class).getAccessToken();
    }

    @Step("Получение login response")
    public static Response getLoginResponse(String phone) {
        LoginRequestModel loginBody = setPhoneToLoginBody(phone);
        authRegister(authCookie, loginBody);
        loginBody.setPassword(password);
       return authLogin(authCookie, loginBody);
    }

    @Step("Получение referral code пользователя")
    public static String getReferralCode(Response loginResponse) {
        if (loginResponse.body().as(LoginResponseModel.class).getManager()!=null)
            return  loginResponse.body().as(LoginResponseModel.class).getManager().getReferralCode();
        else return null;
    }
    @Step("Получение refreshToken из loginResponse")
    public static String getRefreshTokenFromLoginResponse(Response loginResponse) {
        return loginResponse.getCookie("refresh_token");
    }
    @Step("Получение id пользователя")
    public static int getUserId (String phone) {
        Response loginResponse = getLoginResponse(phone);
        return loginResponse.body().as(LoginResponseModel.class).getUser().getId();
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
