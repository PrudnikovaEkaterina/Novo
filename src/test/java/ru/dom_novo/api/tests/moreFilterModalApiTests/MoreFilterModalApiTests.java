package ru.dom_novo.api.tests.moreFilterModalApiTests;

import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.dom_novo.api.enumsApi.ReleaseDateEnum;
import ru.dom_novo.api.enumsApi.RenovationEnum;
import ru.dom_novo.api.models.buildingModels.*;
import ru.dom_novo.api.models.sitemap.geo.RootSitemapGeoModel;
import ru.dom_novo.api.steps.moreFilterModalApiSteps.MoreFilterModalApiSteps;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.dataBase.services.FlatService;
import ru.dom_novo.regexp.RegexpMeth;

import java.util.*;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;

@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class MoreFilterModalApiTests {

    @Test
    @DisplayName("Применить фильтр Площадь до. Проверить, что в найденных ЖК square_m2_from меньше заданного фильтра")
    void searchSquareMax() {
        double squareMax = 15;
        MoreFilterModalApiSteps.getBuildingListWithFilterSquareMax(squareMax);
    }

    @Test
    @DisplayName("Применить фильтр Площадь от. Проверить, что в найденных ЖК square_m2_to больше заданного фильтра")
    void searchSquareMin() {
        double squareMin = 400;
        MoreFilterModalApiSteps.getBuildingListWithFilterSquareMin(squareMin);
    }

    @Test
    @DisplayName("Применить фильтр Этаж c. Проверить, что в найденных ЖК есть квартиры, где этаж равен или больше фильтра")
    void searchFloorMin() {
        int floorMin = 50;
        MoreFilterModalApiSteps.checkEqualityTwoLists(MoreFilterModalApiSteps.getBuildingListWithFilterFloorMin(floorMin),
                MoreFilterModalApiSteps.selectBuildingListWithFilterFloorMin(floorMin));
    }

    @Test
    @DisplayName("Применить фильтр Только апартаменты и проверить, что у всех найденных объектов apartments = 1")
    void searchOnlyApartments() {
        MoreFilterModalApiSteps.checkBuildingListWithFilterOnlyApartments();
    }

    @Test
    @DisplayName("Применить фильтр Без апартаментов и проверить, что у всех найденных объектов apartments = 0")
    void searchWithoutApartments() {
        MoreFilterModalApiSteps.checkBuildingListWithFilterWithoutApartments();
    }

    @CsvSource(value = {"1, mortgage",
            "2, military_mortgage",
            "3, subsidy",
            "4, installment"})
    @ParameterizedTest(name = "Применить фильтр Cпособ оплаты {1}. Проверить, что в найденных ЖК есть квартиры, где {1} = 1")
    void searchMortgage(String data1, String data2) {
        List<Integer> apiList = MoreFilterModalApiSteps.getBuildingListWithFilterMortgage(data1);
        List<Integer> dataList = MoreFilterModalApiSteps.selectBuildingListWithFilterMortgage(data2);
        MoreFilterModalApiSteps.checkEqualityTwoLists(apiList, dataList);

    }

    @EnumSource(RenovationEnum.class)
    @ParameterizedTest(name = "Применить фильтр Отделка = {0}. Проверить, что в найденных ЖК есть квартиры c соответствующим типом отделки")
    @Comment("Тест падает для ЖК 10375, комментарий в задаче https://tracker.yandex.ru/NOVODEV-594#649997a4a7cc4e7f81697263")
    void searchRenovation(RenovationEnum renovationEnum) {
        String renovationId = renovationEnum.id;
        List<Integer> apiList = MoreFilterModalApiSteps.getBuildingListWithFilterRenovation(renovationId);
        System.out.println(apiList.size());
        List<Integer> dataList = FlatService.getBuildingIdFromFlatsWithFilterRenovation(renovationId);
        System.out.println(dataList.size());
        MoreFilterModalApiSteps.checkEqualityTwoLists(apiList, dataList);

    }

    @Test
    @DisplayName("Применить фильтр Cрок сдачи 'Сдан' и проверить поисковую выдачу на соответствие фильтру")
    void searchWithFilterReleaseDate1AndVerifyResult() {
        String releaseDate = "1";
        String expectedReleaseDate = "Сдан";
        List<Integer> listBuildingId = MoreFilterModalApiSteps.getBuildingListWithFilterReleaseDate(releaseDate);
        for (Integer integer : listBuildingId) {
            List<Integer> listDistinctHouseId = BuildingDao.selectDistinctHouseId(integer);
            if (listDistinctHouseId.get(0)!=null)
                MoreFilterModalApiSteps.getAndVerifyReleaseDateList(listDistinctHouseId, expectedReleaseDate);
            else {
                String releaseDateValue = MoreFilterModalApiSteps.getReleaseDate(integer);
                MoreFilterModalApiSteps.verifyActualContainsExpected(releaseDateValue, expectedReleaseDate);
            }
        }
    }

    @Test
    @DisplayName("Применить фильтр Cрок сдачи 'Строится' и проверить поисковую выдачу на соответствие фильтру")
    @Comment("Тест падает для ЖК 16786, комментарий в задаче https://tracker.yandex.ru/NOVODEV-683#64e743418d77536801c4d90e")
//  помимо срока сдачи проверяем Стадию строительства, если есть корпуса, то смотрим стадию строительства только по ним
    void searchWithFilterReleaseDate2AndVerifyResult() {
//      Получить список id ЖК со сроком сдачи Сдан;
//      Получить список корпусов с квартирами для каждого ЖК
//        Если список корпусов не пустой:
//          Создаем Map, где ключ - id корпуса, значение - List<String>;
//              Если количество квартир, привязанных непосредственно к ЖК, а не к корпусу > 0, то
//               - получаем стадию строительства родительского ЖК
//               - получаем стадию строительсва корпусов, добавляем в Map
//               - если стадия строительсва корпуса null, то присваиваем ей стадию строительства родителя
//               - получаем срок сдачи для каждого корпуса, добавляем в Map
//               - Проверяем, есть ли в Map хоть 1 ключ, где срок сдачи != "Сдан" и стадия строительства != Сдан, В проекте, Заморожено
//              Иначе (количество квартир, привязанных непосредственно к ЖК, а не к корпусу == 0):
//               - получаем стадию строительсва корпусов, добавляем в Map
//               - получаем срок сдачи для каждого корпуса, добавляем в Map
//               - Проверяем, есть ли в Map хоть 1 ключ, где срок сдачи != "Сдан" и стадия строительства != Сдан, В проекте, Заморожено
//          Иначе:
//          - получаем стадию строительства  ЖК
//          - Проверяем, что стадия строительства != Сдан, В проекте, Заморожено
//          - получаем срок сдачи  ЖК
//          - Проверяем, что срок сдачи содержит слово "квартал"
        String releaseDate = "2";
        String notExpectedReleaseDate = "Сдан";
        String expectedReleaseDate = "квартал";
        String releaseStateFirst = "Сдан";
        String releaseStateSecond = "В проекте";
        String releaseStateThird = "Заморожено";
        List<Integer> listBuildingId = MoreFilterModalApiSteps.getBuildingListWithFilterReleaseDate(releaseDate);
        for (Integer buildingId : listBuildingId) {
            if (buildingId==16786){
                break;}
            List<Integer> listDistinctHouseId = BuildingDao.selectDistinctHouseId(buildingId);
            if (listDistinctHouseId.get(0)!=null) {
                Map<Integer, List<String>> houseIdMap = new HashMap<>();
                if(BuildingDao.selectCountFromFlatsWhereHouseIdIsNull(buildingId)>0) {
                   String releaseStateBuilding = MoreFilterModalApiSteps.getReleaseState(buildingId);
                   for (Integer houseId:listDistinctHouseId) {
                       if (houseId != null) {
                           Response response = MoreFilterModalApiSteps.getResponse(houseId);
                           String releaseStateHouse = MoreFilterModalApiSteps.getReleaseStateFromResponse(response);
                           String releaseDateHouse = MoreFilterModalApiSteps.getReleaseDateFromResponse(response);
                           if (releaseStateHouse != null)
                               houseIdMap.put(houseId, List.of(releaseStateHouse, releaseDateHouse));
                           else
                               houseIdMap.put(houseId, List.of(releaseStateBuilding, releaseDateHouse));
                           }
                       Assertions.assertTrue(houseIdMap.values().stream().noneMatch(el -> el.get(0).equals(releaseStateFirst) && el.get(0).equals(releaseStateSecond) && el.get(0).equals(releaseStateThird) && el.get(1).equals(notExpectedReleaseDate)));
                       }
                   }
                else {
                    for (Integer houseId:listDistinctHouseId){
                        if (houseId!=null){
                            Response response = MoreFilterModalApiSteps.getResponse(houseId);
                            String releaseStateHouse = MoreFilterModalApiSteps.getReleaseStateFromResponse(response);
                            String releaseDateHouse = MoreFilterModalApiSteps.getReleaseDateFromResponse(response);
                            houseIdMap.put(houseId, List.of(releaseStateHouse, releaseDateHouse));
                            Assertions.assertTrue(houseIdMap.values().stream().noneMatch(el->el.get(0).equals(releaseStateFirst)&&el.get(0).equals(releaseStateSecond)&&el.get(0).equals(releaseStateThird)&&el.get(1).equals(notExpectedReleaseDate)));
                }}
            }}
            else {
                Response response = MoreFilterModalApiSteps.getResponse(buildingId);
                String releaseStateValue = MoreFilterModalApiSteps.getReleaseStateFromResponse(response);
                String releaseDateValue = MoreFilterModalApiSteps.getReleaseDateFromResponse(response);
                MoreFilterModalApiSteps.verifyActualContainsExpected(releaseDateValue, expectedReleaseDate);
                MoreFilterModalApiSteps.verifyActualNotEqualsExpected(releaseStateValue, releaseStateFirst, releaseStateSecond, releaseStateThird);
            }
        }
    }

    @ParameterizedTest(name = "Применить фильтр Cрок сдачи {0} и проверить поисковую выдачу на соответствие фильтру")
    @EnumSource(ReleaseDateEnum.class)
    void searchWithFilterReleaseDateAndVerifyResult(ReleaseDateEnum releaseDateEnum) {
        String releaseDate = releaseDateEnum.name;
        int year = Integer.parseInt(releaseDate.substring(0, 4));
        int quarter = Integer.parseInt(releaseDate.substring(4));
        String expectedValue = "Сдан";
        List<Integer> listBuildingId = MoreFilterModalApiSteps.getBuildingListWithFilterReleaseDate(releaseDate);
        for (Integer integer : listBuildingId) {
            List<Integer> listDistinctHouseId = BuildingDao.selectDistinctHouseId(integer);
            if (listDistinctHouseId.get(0)!=null) {
                List<String> listReleaseDate = new ArrayList<>();
                for (Integer value : listDistinctHouseId) {
                    if (value!=null){
                    String releaseDateValue = MoreFilterModalApiSteps.getReleaseDate(value);
                    listReleaseDate.add(releaseDateValue);}
                }
                if (!listReleaseDate.contains(null)) {
                    if (listReleaseDate.stream().noneMatch(el -> el.contains(expectedValue))) {
                        List<List<Integer>> collect = listReleaseDate.stream().map(RegexpMeth::getListNumbersFromString).collect(Collectors.toList());
                        System.out.println(collect);
                        if (collect.stream().noneMatch(el -> el.get(1) < year)) {
                            Assertions.assertTrue(collect.stream().filter(el -> el.get(1) == year).anyMatch(el -> el.get(0) <= quarter));
                        }
                    }
                }
            } else {
                String releaseDateValue = MoreFilterModalApiSteps.getReleaseDate(integer);
                if (!releaseDateValue.contains(expectedValue)) {
                    List<Integer> numbers = RegexpMeth.getListNumbersFromString(releaseDateValue);
                    Assertions.assertTrue(numbers.get(0) <= quarter && numbers.get(1) <= year);
                }
            }
        }

    }

}
