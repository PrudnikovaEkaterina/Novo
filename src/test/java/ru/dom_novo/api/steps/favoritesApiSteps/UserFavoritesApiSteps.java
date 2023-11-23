package ru.dom_novo.api.steps.favoritesApiSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.buildingModels.BuildingDto;
import ru.dom_novo.api.models.buildingModels.BuildingDataDto;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;
import ru.dom_novo.testData.GenerationData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.helpers.CustomAllureListener.withCustomTemplates;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class UserFavoritesApiSteps {
    @Step("Получить список id избранных ЖК пользователя")
    public static List<Integer> getUserFavoritesBuildingsIdList(String phoneNumber) {
        String accessToken = AuthApiSteps.getAccessTokenUsePhoneNumber(phoneNumber);
        BuildingDataDto dataBuilding = given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/me/favorites/buildings/")
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class);
        return dataBuilding.getData().stream().map(BuildingDto::getId).collect(Collectors.toList());
    }

    @Step("Получить map Price.From и TitleEng избранных ЖК пользователя")
    public static Map<Long, String> getUserFavoritesBuildingsPriceFromAndTitleEngMap(String accessToken) {
        BuildingDataDto dataBuilding = given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .param("region_code[]", 77)
                .param("region_code[]", 50)
                .when()
                .get("/api/buildings/favorites/")
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class);
        return dataBuilding.getData().stream().collect(Collectors.toMap(el->el.getFlats().getPrice().getFrom(), BuildingDto::getTitleEng));
    }

    @Step("Получить map SquareM2.From и TitleEng избранных ЖК пользователя")
    public static Map<String, Double> getUserFavoritesBuildingSquareM2FromAndTitleEngMap(String accessToken) {
        List<BuildingDto> data = given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .param("region_code[]", 77)
                .param("region_code[]", 50)
                .when()
                .get("/api/buildings/favorites/")
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class).getData();
        Map<String, Double> map = new HashMap<>();
        for (BuildingDto el : data) {
            double squareM2From;
            if (el.getFlats().getSquareM2().getFrom()!=null){
                squareM2From = el.getFlats().getSquareM2().getFrom();
            }
            else squareM2From=0;

            map.put(el.getTitleEng(), squareM2From);
        }
       return map;
    }

    @Step("Получить map SquareM2.From и TitleEng избранных ЖК пользователя")
    public static Map<String, Double> getUserFavoritesBuildingPriceM2FromAndTitleEngMap(String accessToken) {
        List<BuildingDto> data = given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .param("region_code[]", 77)
                .param("region_code[]", 50)
                .when()
                .get("/api/buildings/favorites/")
                .then()
                .spec(responseSpec200)
                .extract().as(BuildingDataDto.class).getData();
        Map<String, Double> map = new HashMap<>();
        for (BuildingDto el : data) {
            double priceM2;
            if (el.getFlats().getPriceM2().getFrom()!=null){
                priceM2 = el.getFlats().getPriceM2().getFrom();
            }
            else priceM2 = 0;

            map.put(el.getTitleEng(), priceM2);
        }
        return map;
    }


    @Step("Отсортировать Map по ключу в порядке возрастания и вернуть список значений")
    public static List<String> sortMapByKeyAscAndReturnValue(Map<Long, String> map) {
        return map.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Step("Отсортировать Map по ключу в порядке убывания и вернуть список значений")
    public static List<String> sortMapByKeyDescAndReturnValue(Map<Long, String> map) {
        return map.entrySet().stream().sorted(Map.Entry.<Long, String> comparingByKey().reversed()).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Step("Отсортировать Map по значению в порядке убывания и вернуть список ключей")
    public static List<String> sortMapByValueDescAndReturnKey(Map<String, Double> map) {
        return  map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    @Step("Отсортировать Map по значению в порядке возрастания и вернуть список ключей")
    public static List<String> sortMapByValueAscAndReturnKey(Map<String, Double> map) {
        return  map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());
    }



    @Step("Добавить пользователю ЖК в избранное")
    public static void addBuildingToUserFavoritesUsePhoneNumber(String phoneNumber) {
        String accessToken = AuthApiSteps.getAccessTokenUsePhoneNumber(phoneNumber);
        int buildingId = GenerationData.setRandomBuildingId();
        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("api/me/favorites/buildings/"+buildingId)
                .then()
                .spec(responseSpec200);
    }
    @Step("Добавить пользователю ЖК в избранное")
    public static void addBuildingToUserFavoritesUseAccessToken(String accessToken) {
        int buildingId = GenerationData.setRandomBuildingId();
        given()
                .filter(withCustomTemplates())
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("api/me/favorites/buildings/"+buildingId)
                .then()
                .spec(responseSpec200);
    }
}
