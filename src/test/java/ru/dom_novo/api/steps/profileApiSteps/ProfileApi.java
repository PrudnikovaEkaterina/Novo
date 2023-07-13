package ru.dom_novo.api.steps.profileApiSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.authModels.UserModel;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static ru.dom_novo.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class ProfileApi {
    @Step("Изменить имя пользователя")
    public static void changeUsername (String accessToken, String userName) {
        String body = "{\"name\":\""+userName+"\"}";
        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .patch("/api/me/profile/")
                .then()
                .spec(responseSpec200)
                .body("name", is(userName));
    }

    @Step("Изменить Email пользователя")
    public static void changeEmail (String accessToken, String email) {
      String body = "{\"email\":\""+email+"\"}";
        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .patch("/api/me/profile/")
                .then()
                .spec(responseSpec200)
                .body("email", is(email));
    }

    @Step("Изменить имя и Email пользователя")
    public static void changeUsernameAndEmail (String accessToken, String userName, String email) {
        UserModel user =new UserModel();
        user.setName(userName);
        user.setEmail(email);
        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(user)
                .when()
                .patch("/api/me/profile/")
                .then()
                .spec(responseSpec200)
                .body("name", is(userName))
                .body("email", is(email));
    }
}
