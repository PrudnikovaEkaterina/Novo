package ru.dom_novo.api.steps.sitemapSteps;

import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class SitemapBuildingsSteps {
    @Step("Получить список slug ЖК из роута /api/sitemap/xml/buildings")
    public static List<String> getSlugList () {
        return given()
                .spec(requestSpec)
                .basePath("/api/sitemap/xml/buildings")
                .param("limit", 500)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.slug");
    }
}
