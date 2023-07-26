package ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import ru.dom_novo.api.models.marketcallBundle.DataModel;
import ru.dom_novo.api.models.marketcallBundle.OfferModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class MarketcallBundleApi {
    @Step("Получить данные из https://www.marketcall.ru/api/v1/newbuilding-bundles")
    public static DataModel getMarketcallBundles() {
        return given()
                .baseUri("https://www.marketcall.ru")
                .log().all()
                .contentType(ContentType.JSON)
                .cookie("laravel_session",
                        "eyJpdiI6Im12Z0RWRW1VN0RDdTJ6VU14MUMya3c9PSIsInZhbHVlIjoielhZc1NheVI5QmI5Q2h0QzhJRFZEY2R4MElzY091YlRyZ2RzQUpBbDFCYVB6VG5sZTlrdTZYWW9pMWs1Wmp4V2F0Y2h5OEcrV0RvU1l6VXdsWkU4VFNtUHZBK0tNUlJEc1Y4MVp4U1VqR0RvMGNtV2s0MW9qWmhzeDlnWGdkcTUiLCJtYWMiOiJjODYyNTA1NWU0YmJjMjkzY2UzYTNkNGQzNmE1MzJjNTM0YTZkNzViNjRmZjNlOGFhMjRlNmMwOGU2ZDk1YWExIn0%3D")
                .header("X-Marketcall-Token", "e13d306a-df68-11ed-b5ea-0242ac120002")
                .when()
                .get("/api/v1/newbuilding-bundles")
                .then()
                .spec(responseSpec200).extract().as(DataModel.class);
    }

    @Step("Получить общее количество ЖК, выбрав из каждого бандла уникальные объекты")
    public static int getCountBuildingsFromBundle() {
        DataModel dataModel = getMarketcallBundles();
        int count =0;
        for (int i=0; i<dataModel.getData().size(); i++){
           count=count+ dataModel.getData().get(i).getOffers().stream().map(OfferModel::getMoveNewbuildingSecondaryId).collect(Collectors.toSet()).size();
        }
        return count;
    }

    @Step("Получить общее количество уникальных ЖК из Marketcall Bundles")
    public static int getCountDistinctBuildingsFromMarketcallBundles() {
        DataModel dataModel = getMarketcallBundles();
        Set<String> setAll = new HashSet<>();
        for (int i=0; i<dataModel.getData().size(); i++){
          setAll.addAll(dataModel.getData().get(i).getOffers().stream().map(OfferModel::getMoveNewbuildingSecondaryId).collect(Collectors.toSet()));
        }
          return setAll.size();
    }

    @Step("Получить список  названий bundles")
    public static List<String> getTitleBundles() {
        DataModel dataModel = getMarketcallBundles();
        List<String> titleList = new ArrayList<>();
        for (int i=0; i<dataModel.getData().size(); i++){
            titleList.add(dataModel.getData().get(i).getTitle());
        }
        return titleList;
    }

    @Step("Получить список  id bundles")
    public static List<Integer> getIdBundles() {
        DataModel dataModel = getMarketcallBundles();
        List<Integer> idList = new ArrayList<>();
        for (int i=0; i<dataModel.getData().size(); i++){
            idList.add(dataModel.getData().get(i).getId());
        }
        return idList;
    }
}
