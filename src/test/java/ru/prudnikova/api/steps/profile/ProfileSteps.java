package ru.prudnikova.api.steps.profile;

import io.qameta.allure.Step;
import ru.prudnikova.api.models.auth.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static ru.prudnikova.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.prudnikova.api.specifications.Specification.requestSpec;
import static ru.prudnikova.api.specifications.Specification.responseSpec200;

public class ProfileSteps {
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
        User user =new User();
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
