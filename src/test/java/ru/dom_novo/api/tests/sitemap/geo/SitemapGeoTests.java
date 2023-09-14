package ru.dom_novo.api.tests.sitemap.geo;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.sitemapSteps.SitemapGeoSteps;
import ru.dom_novo.dataBase.dao.BuildingDao;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@Tag("Api")
@Owner("PrudnikovaEkaterina")
@TmsLink("https://tracker.yandex.ru/NOVODEV-688")
@Disabled
public class SitemapGeoTests {
    @Test
    @Description("Проверить, что данные роута api/sitemap/xml/geo с типом city соответсвуют гео для ЖК с предложениями")
    void checkCity() {
//        1. Получить список уникальных gar_object_id для ЖК с предложениями из таблицы buildings;
//        2. Для кажого gar_object_id получить PATH из таблицы gar_MUN_HIERARCHY;
//        3. Собрать уникальные значения PATH в set
//        4. Для каждого уникального PATH получить title_eng из таблицы gar_ADDRESSOBJECTS и собрать эти значения в список;
//        (учесть что LEVEL должен быть <=5)
//        5. Из роута api/sitemap/xml/geo собрать все title_eng для  "type": "city"
//        6. Сравнить 2 списка на идентичность;

        List<Integer> buildingGarObjectIdList = BuildingDao.selectDistinctBuildingGarObjectId();
        Set<String> pathSet = new HashSet<>();
        for (Integer id : buildingGarObjectIdList) {
            String path = BuildingDao.selectPathFromGarMunHierarchy(id).get(0);
            List<String> pathList = List.of(path.split("\\."));
            pathSet.addAll(pathList);
        }
        List<String> titleEngListExpected = new ArrayList<>();
        for (String el:pathSet){
            String titleEng = BuildingDao.selectTitleEngFromGarAddressObjects(el);
            if(titleEng!=null)
                titleEngListExpected.add(titleEng);
        }
        Collections.sort(titleEngListExpected);
        List<String> titleEngListForTypeCityActual = SitemapGeoSteps.getTitleEngListForTypeCity();
        Collections.sort(titleEngListForTypeCityActual);
        assertThat(titleEngListForTypeCityActual, is(titleEngListExpected));

    }
    @Test
    @Description("Проверить, что данные роута api/sitemap/xml/geo с типом district соответсвуют районам для ЖК с предложениями")
    void checkDistrict() {
//        1. Получить список уникальных id ЖК с предложениями;
//        2. Для кажого id из полученного списка получить значение location / district;
//        3. Собрать уникальные значения district в set
//        4. Из роута api/sitemap/xml/geo собрать все id для  "type": "district"
//        6. Сравнить 2 списка на идентичность;

        List<Integer> buildingIdList = BuildingDao.selectDistinctBuildingIdFromFlats();
        Set<Integer> districtIdSetExpected = new HashSet<>();
        for (Integer id : buildingIdList) {
            int district = CardNovostroykiApiSteps.getDistrict(id);
            if (district!=0){
                districtIdSetExpected.add(district);
            }
        }
        List<Integer> districtIdSetActual = SitemapGeoSteps.getIdListForTypeDistrict();
        Assertions.assertTrue(districtIdSetActual.containsAll(districtIdSetExpected));

    }

    @Test
    @Description("Проверить, что данные роута api/sitemap/xml/geo с типом road соответсвуют road у ЖК с предложениями")
    void checkRoad() {
//        1. Получить список уникальных id ЖК с предложениями;
//        2. Для кажого id из полученного списка получить значение near / road / id;
//        3. Собрать уникальные значения road_id в set
//        4. Из роута api/sitemap/xml/geo собрать все id для  "type": "road"
//        6. Сравнить 2 списка на идентичность;

        List<Integer> buildingIdList = BuildingDao.selectDistinctBuildingIdFromFlats();
        Set<Integer> roadIdSetExpected = new HashSet<>();
        for (Integer id : buildingIdList) {
            List<Integer> roadIdList = CardNovostroykiApiSteps.getRoadIdList(id);
            roadIdSetExpected.addAll(roadIdList);
        }
        List<Integer> roadIdSetActual = SitemapGeoSteps.getIdListForTypeRoad();
        Assertions.assertTrue(roadIdSetActual.containsAll(roadIdSetExpected));

    }
    @Test
    @Description("Проверить, что данные роута api/sitemap/xml/geo с типом station соответствуют станциям метро у ЖК с предложениями")
    void checkStation() {
//        - Получить список уникальных id ЖК с предложениями;
//        - Для каждого id из полученного списка получить значение near / station / id;
//        - Собрать уникальные значения station_id в set
//        - Из роута api/sitemap/xml/geo собрать все id для  "type": "station"
//        - Сравнить 2 списка на идентичность;
        List<Integer> buildingIdList = BuildingDao.selectDistinctBuildingIdFromFlats();
        Set<Integer> stationIdSetExpected = new HashSet<>();
        for (Integer id : buildingIdList) {
            List<Integer> stationIdList = CardNovostroykiApiSteps.getStationIdList(id);
            stationIdSetExpected.addAll(stationIdList);
        }
        List<Integer> stationIdSetActual = SitemapGeoSteps.getIdListForTypeStation();
        Assertions.assertTrue(stationIdSetActual.containsAll(stationIdSetExpected));
    }
}
