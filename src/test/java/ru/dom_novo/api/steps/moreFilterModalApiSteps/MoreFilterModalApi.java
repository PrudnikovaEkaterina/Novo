package ru.dom_novo.api.steps.moreFilterModalApiSteps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import ru.dom_novo.dataBase.services.FlatService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class MoreFilterModalApi {

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

    @Step("Получить список ЖК с фильтром Без апартаментов. Проверить, что все объекты, в полученном списке, содержат флаг apartments = 0")
    public static void checkBuildingListWithFilterWithoutApartments() {
        given()
                .spec(requestSpec)
                .basePath("/api/buildings/")
                .param("region_code[]", 50)
                .param("region_code[]", 77)
                .param("apartments", -1)
                .get()
                .then()
                .spec(responseSpec200)
                .body("data.apartments", everyItem(equalTo(0)));
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
    public static List<Integer> getBuildingListWithFilterRenovation(String renovation) {
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


}


