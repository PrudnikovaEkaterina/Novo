package ru.dom_novo.api.steps.moreFilterModalApiSteps;

import io.qameta.allure.Step;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import ru.dom_novo.dataBase.services.FlatService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class MoreFilterModalApiSteps {

    @Step("Получить список ЖК с фильтром Площадь до. Проверить, что в каждом найденном ЖК square_m2_from меньше, чем {squareMax}")
    public static void getBuildingListWithFilterSquareMax(double squareMax) {
        given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("square_max", squareMax)
                .get()
                .then()
                .spec(responseSpec200)
                .body("data.flats.square_m2.from.collect { it as double }", everyItem(lessThan(squareMax)));
    }

    @Step("Получить список ЖК с фильтром Площадь от. Проверить, что в каждом найденном ЖК square_m2_to больше, чем {squareMin} ")
    public static void getBuildingListWithFilterSquareMin(double squareMin) {
        given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("square_min", squareMin)
                .get()
                .then()
                .spec(responseSpec200)
                .body("data.flats.square_m2.to.collect { it as double }", everyItem(greaterThan(squareMin)));
    }

    @Step("Получить список ЖК, применив фильтр Этаж c")
    public static List<Integer> getBuildingListWithFilterFloorMin(int floorMin) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("floor_min", floorMin)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.id");
    }

    @Step("Получить список ЖК из БД, в которых есть квартиры, где floor >= {floorMin}")
    public static List<Integer> selectBuildingListWithFilterFloorMin(int floorMin) {
        return FlatService.getBuildingIdFromFlatsWhereFloorGreaterOrEqualsFloorUnit(floorMin);
    }

    @Step("Получить список ЖК с фильтром Только апартаменты. Проверить, что все объекты, в полученном списке, содержат флаг apartments = 1")
    public static void checkBuildingListWithFilterOnlyApartments() {
        given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("apartments", 1)
                .get()
                .then()
                .spec(responseSpec200)
                .body("data.apartments", everyItem(equalTo(1)));
    }

    @Step("Получить список ЖК со способом оплаты {paymentMethod}")
    public static List<Integer> getBuildingListWithFilterMortgage(String paymentMethod) {
        List<Integer> listAll = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("payment_methods[]", paymentMethod)
                .param("per_page", 1)
                .get();
        Assertions.assertEquals(200, response.statusCode());
        int pageCountInt = response.path("meta.total");
        int pageCountDouble = (int) Math.round((double) pageCountInt / 100);
        for (int i = 1; i < pageCountDouble+2; i++) {
            List<Integer> listPage = given()
                    .spec(requestSpec)
                    .basePath("/api/buildings/")
                    .param("region_code[]", 50)
                    .param("region_code[]", 77)
                    .param("payment_methods[]", paymentMethod)
                    .param("per_page", 100)
                    .param("page", i)
                    .get()
                    .then()
                    .extract().path("data.id");
            listAll.addAll(listPage);
        }
        return listAll;
    }

    @Step("Получить список ЖК из БД, в которых есть квартиры со способом оплаты {paymentMethod}")
    public static List<Integer> selectBuildingListWithFilterMortgage(String paymentMethod) {
        return FlatService.getBuildingIdFromFlatsWithFilterPaymentMethod(paymentMethod);
    }

    @Step("Проверить равенство двух списков")
    public static void checkEqualityTwoLists(List<Integer> apiList, List<Integer> dataList) {
        List<Integer> differences =dataList.stream()
                .filter(element -> !apiList.contains(element))
                .collect(Collectors.toList());
        differences.forEach(System.out::println);
        assert apiList.containsAll(dataList);
    }

    @Step("Получить список ЖК renovation = {renovation}")
    public static List<Integer> getBuildingIdListWithFilterRenovation(String renovation) {
        List<Integer> listAll = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("renovation[]", renovation)
                .param("per_page", 1)
                .get();
        Assertions.assertEquals(200, response.statusCode());
        int totalItem = response.path("meta.total");
        int pageCount = (int) Math.round((double) totalItem / 100);
        for (int i = 1; i <pageCount+2; i++) {
            List<Integer> listPage = given()
                    .spec(requestSpec)
                    .basePath("/api/buildings/")
                    .param("region_code[]", 50)
                    .param("region_code[]", 77)
                    .param("renovation[]", renovation)
                    .param("per_page", 100)
                    .param("page", i)
                    .get()
                    .then()
                    .extract().path("data.id");
            listAll.addAll(listPage);
        }
        return listAll;
    }

    @Step("Получить список id ЖК со сроком сдачи = {releaseDate}")
    public static List<Integer> getBuildingListWithFilterReleaseDate(String releaseDate) {
        List<Integer> listAll = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("release_date", releaseDate)
                .param("per_page", 1)
                .get();
        Assertions.assertEquals(200, response.statusCode());
        int totalItem = response.path("meta.total");
        int pageCount = (int) Math.round((double) totalItem / 100);
        for (int i = 1; i <pageCount+2; i++) {
            List<Integer> listPage = given()
                    .spec(requestSpec)
                    .basePath("/api/buildings/")
                    .param("region_code[]", 50)
                    .param("region_code[]", 77)
                    .param("release_date", releaseDate)
                    .param("per_page", 100)
                    .param("page", i)
                    .get()
                    .then()
                    .extract().path("data.id");
            listAll.addAll(listPage);
        }
        return listAll;
    }

    @Step("Получить срок сдачи для ЖК с id = {buildingId}")
    public static String getReleaseDate(int buildingId) {
        return given()
                            .spec(requestSpec)
                            .basePath("/api/buildings/" + buildingId)
                            .get()
                            .then()
                            .extract().path("data.release_date");
    }
    @Step("Получить значение apartments ЖК с id = {buildingId}")
    public static Integer getApartments(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .extract().path("data.apartments");
    }

    @Step("Получить Response для ЖК с id = {buildingId}")
    public static Response getResponse(int buildingId) {
       return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .extract().response();
    }
    @Step("Получить срок сдачи для ЖК с id = {buildingId}")
    public static String getReleaseDateFromResponse(Response response) {
       return response.path("data.release_date");
    }

    @Step("Получить стадию строительства для ЖК с id = {buildingId}")
    public static String getReleaseStateFromResponse(Response response) {
      return response.path("data.release_state");
    }

    @Step("Получить список сроков сдачи корпусов")
    public static void getAndVerifyReleaseDateList(List<Integer> listDistinctHouseId, String expectedReleaseDate ) {
        List<String> listReleaseDate = new ArrayList<>();
        for (Integer value : listDistinctHouseId) {
            if(value!=null){
                String releaseDateValue = MoreFilterModalApiSteps.getReleaseDate(value);
                System.out.println(releaseDateValue);
                listReleaseDate.add(releaseDateValue);}
        }
        verifyAnyMatch(listReleaseDate, expectedReleaseDate);
    }
    @Step("Проверить, что хотя бы одно значение из списка содержит {expectedValue}")
    public static void verifyAnyMatch(List<String> listValue, String expectedValue) {
        if (listValue.contains(null)){
            if(listValue.stream().noneMatch(el -> el.contains(expectedValue)))
                System.out.println("В этих корпусах нет слова Сдан"+listValue);
            Assertions.assertTrue(listValue.stream().anyMatch(el -> el.contains(expectedValue)));
        }
    }

    @Step("Проверить, что актуальное значение содержит {expectedValue}")
    public static void verifyActualContainsExpected(String actualValue, String expectedValue) {
        if (actualValue!=null){
            Assertions.assertTrue(actualValue.contains(expectedValue));}
    }

    @Step("Получить стадию строительства для ЖК с id = {buildingId}")
    public static String getReleaseState(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .extract().path("data.release_state");
    }

    @Step("Проверить, что актуальное значение не равно {expectedValue}")
    public static void verifyActualNotEqualsExpected(String actualValue, String valueFirst, String valueSecond, String valueThird) {
        if (actualValue!=null){
           assertThat(actualValue, not(valueFirst));
           assertThat(actualValue, not(valueSecond));
           assertThat(actualValue, not(valueThird));
        }
    }


}


