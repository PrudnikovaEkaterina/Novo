package ru.dom_novo.api.steps.sitemapGeoSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.sitemap.geo.Datum;
import ru.dom_novo.api.models.sitemap.geo.RootSitemapGeoModel;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class SitemapGeoSteps {
    @Step("Получить список ЖК с фильтром Станция метро = {station}")
    public static RootSitemapGeoModel getData () {
        return given()
                .spec(requestSpec)
                .basePath("/api/sitemap/xml/geo")
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(RootSitemapGeoModel.class);
    }
    @Step("Получить список id для типа данных station")
    public static List<Integer> getIdListForTypeStation (RootSitemapGeoModel data) {
        return data.getData().stream().filter(el->el.getType().equals("station")).collect(Collectors.toList()).stream().map(Datum::getId).collect(Collectors.toList());
    }
}
