package ru.dom_novo.api.steps.sitemapSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.sitemap.geo.DatumSitemapGeoModel;
import ru.dom_novo.api.models.sitemap.geo.RootSitemapGeoModel;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class SitemapGeoSteps {
    @Step("Получить данные из роута /api/sitemap/xml/geo")
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
    public static List<Integer> getIdListForTypeStation () {
        RootSitemapGeoModel data = getData();
        return data.getData().stream().filter(el->el.getType().equals("station")).collect(Collectors.toList()).stream().map(DatumSitemapGeoModel::getId).collect(Collectors.toList());
    }
    @Step("Получить список title_eng для типа данных city")
    public static List<String> getTitleEngListForTypeCity () {
        RootSitemapGeoModel data = getData();
        return data.getData().stream().filter(el->el.getType().equals("city")).collect(Collectors.toList()).stream().map(DatumSitemapGeoModel::getTitleEng).collect(Collectors.toList());
    }
    @Step("Получить список id для типа данных district")
    public static List<Integer> getIdListForTypeDistrict () {
        RootSitemapGeoModel data = getData();
        return data.getData().stream().filter(el->el.getType().equals("district")).collect(Collectors.toList()).stream().map(DatumSitemapGeoModel::getId).collect(Collectors.toList());
    }

    @Step("Получить список id для типа данных road")
    public static List<Integer> getIdListForTypeRoad () {
        RootSitemapGeoModel data = getData();
        return data.getData().stream().filter(el->el.getType().equals("road")).collect(Collectors.toList()).stream().map(DatumSitemapGeoModel::getId).collect(Collectors.toList());
    }
}
