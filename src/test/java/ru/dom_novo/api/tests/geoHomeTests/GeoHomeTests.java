package ru.dom_novo.api.tests.geoHomeTests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.geoHomeSteps.GeoHomeSteps;
import ru.dom_novo.dataBase.dao.BuildingDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Tag("Api")
@Owner("PrudnikovaEkaterina")
@TmsLink("https://tracker.yandex.ru/NOVODEV-756")
public class GeoHomeTests {
    @Test
    @Description("Проверить, что данные роута /api/geo/home/subway соответствуют станциям метро у ЖК с предложениями")
    void checkSubway() {
//        - Получить список id ЖК с предложениями из Москвы и МО;
//        - Для каждого id из полученного списка получить значение near / station / id;
//        - Собрать уникальные значения station_id в set
//        - Из роута /api/geo/home/subway собрать все id
//        - Сравнить 2 списка на идентичность;
        List<Integer> buildingIdList = BuildingDao.selectDistinctBuildingIdFromFlats();
        Set<Integer> subwayIdSetExpected = new HashSet<>();
        for (Integer id : buildingIdList) {
            List<Integer> stationIdList = CardNovostroykiApiSteps.getStationIdList(id);
            subwayIdSetExpected.addAll(stationIdList);
        }
        List<Integer> stationIdSetActual = GeoHomeSteps.getSubwayId();
        Assertions.assertTrue(stationIdSetActual.containsAll(subwayIdSetExpected));
    }

    @Test
    @Description("Проверить, что данные роута /api/geo/home/districts соответствуют станциям районам у ЖК из Москвы с предложениями")
    void checkDistricts() {
//        1. Получить список id ЖК с предложениями из Москвы;
//        2. Для кажого id из полученного списка получить значение location / district;
//        3. Собрать уникальные значения district в set
//        4. Из роута /api/geo/home/districts собрать все id
//        5. Сравнить 2 списка на идентичность;
        int region = 77;
        List<Integer> buildingIdList = BuildingDao.selectDistinctBuildingIdFromFlatsRegion(region);
        Set<Integer> districtIdSetExpected = new HashSet<>();
        for (Integer id : buildingIdList) {
            int district = CardNovostroykiApiSteps.getDistrictId(id);
            if (district!=0){
                districtIdSetExpected.add(district);
            }
        }
        List<Integer> districtIdSetActual = GeoHomeSteps.getDistrictId();
        Assertions.assertTrue(districtIdSetActual.containsAll(districtIdSetExpected));
    }

    @Test
    @Description("Проверить, что данные роута /api/geo/home/cities соответствуют городам  у ЖК из МО с предложениями")
    void checkCities() {
//        1. Получить список id ЖК с предложениями из МО;
//        2. Для кажого id из полученного списка получить значение location / geo / cities / id;
//        3. Собрать уникальные значения cities_id в set
//        4. Из роута /api/geo/home/cities собрать все id
//        5. Сравнить 2 списка на идентичность;
        int region = 50;
        List<Integer> buildingIdList = BuildingDao.selectDistinctBuildingIdFromFlatsRegion(region);
        Set<Integer> citiesIdSetExpected = new HashSet<>();
        for (Integer id : buildingIdList) {
            int cityId = CardNovostroykiApiSteps.getCityId(id);
            if (cityId!=0){
                citiesIdSetExpected.add(cityId);
            }
        }
       List<Integer> citiesIdSetActual = GeoHomeSteps.getCitiesId();
       Assertions.assertTrue(citiesIdSetActual.containsAll(citiesIdSetExpected));
       System.out.println(citiesIdSetExpected.size());
    }
}
