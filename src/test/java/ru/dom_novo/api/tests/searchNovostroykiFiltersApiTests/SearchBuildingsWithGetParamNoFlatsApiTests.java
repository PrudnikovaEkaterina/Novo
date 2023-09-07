package ru.dom_novo.api.tests.searchNovostroykiFiltersApiTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.dom_novo.api.models.buildingModels.RootModel;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps.SearchBuildingsFiltersApi;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.dataBase.services.BuildingService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Owner("PrudnikovaEkaterina")
@Tag("Api")
public class SearchBuildingsWithGetParamNoFlatsApiTests {
    //        no_flats=1 - Режим для брокеров МК. Поиск осуществляется по предложениям ТА и данным из админки Move.
//        ЖК без предложений ТА или цен в админке не выводятся. Это требуемый дополнительный режим.
//        Особенность сортировки для no_flats=1: при применении сортировки по цене или площади (всё, кроме популярности),
//        выдача состоит из 2-х частей, отсортированных независимо друг от друга. В начале идут отсортированные ЖК с предложениями ТА,
//        затем отсортированные по данным админки (кроме тех ЖК, для которых были предложения ТА).
    @Test
    @DisplayName("Проверить коррекность выдачи после добавления гет параметра no_flats=1")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-646")
    void checkSearchWithGetParameterNoFlats1() throws JsonProcessingException {
//  1. Получить список id ЖК из /api/buildings/list_on_map?region_code[]=77&region_code[]=50&no_flats=1
//  2. Получить из базы список id ЖК с предложениями
//  3. Получить из базы список id ЖК без предложений, но с ценами из новой админки;
//     -  если типы комнат ЖК (Properties202Values) содержат одно из ожидаемых значений или PricesTitle содержит одно из ожидаемых значений,
//     то собрать их в список buildingIdListWithPricesAndRoomTypeOrPrices
//  4. Объединить buildingIdListWithPricesAndRoomTypeOrPrices  b buildingIdListWithFlats в один и проверить, что его размер равен размеру списку из апи;
//  5. Отсортировать 2 списка и проверить на равенство;
        String _s = "Студии";
        String _ss = "Свободная планировка";
        String _1 = "1-комнатные";
        String _2 = "2-х комнатные";
        String _3 = "3-х комнатные";
        String _4 = "4-х комнатные";
        String _5 = "5-и комнатные";
        String _6 = "6-и комнатные";
        String _7 = "7-и комнатные";
        String _8 = "8-и комнатные";
        List<Integer> buildingIdListWithGetParameterNoFlats1 = SearchBuildingsFiltersApi.getBuildingIdListWithFilterNoFlats(1);
        List<Integer> buildingIdListWithFlats = BuildingDao.selectDistinctBuildingIdWithFlats();
        List<Integer> buildingIdListWithPrices = BuildingDao.selectDistinctBuildingIdWithPrices();
        List<Integer> buildingIdListWithPricesAndRoomTypeOrPrices = new ArrayList<>();
        for (Integer id : buildingIdListWithPrices) {
            String buildingTypeRoomValues = BuildingService.selectProperties202Values(id);
            String buildingPricesTitle = BuildingService.selectPricesTitle(id);
            if (buildingTypeRoomValues != null && buildingPricesTitle != null) {
                if (buildingTypeRoomValues.contains(_s) || buildingTypeRoomValues.contains(_ss)
                        || buildingTypeRoomValues.contains(_1) || buildingTypeRoomValues.contains(_2)
                        || buildingTypeRoomValues.contains(_3) || buildingTypeRoomValues.contains(_4)
                        || buildingTypeRoomValues.contains(_5) || buildingTypeRoomValues.contains(_6)
                        || buildingTypeRoomValues.contains(_7) || buildingTypeRoomValues.contains(_8)
                        || buildingPricesTitle.contains(_s) || buildingPricesTitle.contains(_ss)
                        || buildingPricesTitle.contains(_1) || buildingPricesTitle.contains(_2)
                        || buildingPricesTitle.contains(_3) || buildingPricesTitle.contains(_4)
                        || buildingPricesTitle.contains(_5) || buildingPricesTitle.contains(_6)
                        || buildingPricesTitle.contains(_7) || buildingPricesTitle.contains(_8)) {
                    buildingIdListWithPricesAndRoomTypeOrPrices.add(id);
                }
            }
        }
        List<Integer> totalBuildingIdList = new ArrayList<>();
        totalBuildingIdList.addAll(buildingIdListWithFlats);
        totalBuildingIdList.addAll(buildingIdListWithPricesAndRoomTypeOrPrices);
        Collections.sort(buildingIdListWithGetParameterNoFlats1);
        Collections.sort(totalBuildingIdList);
        assertThat(buildingIdListWithGetParameterNoFlats1, is(totalBuildingIdList));
    }

    @CsvSource(value = {"4_plus, 4k_plus, 4-х комнатные, 5-и комнатные, 6-и комнатные, 7-и комнатные", "studio, studio, Студии, Свободная планировка, Параметр, Параметр"})
    @ParameterizedTest(name = "Проверить коррекность выдачи после применения фильтра {2} и гет параметра no_flats=1")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-646")
    void checkSearchWithGetParameterNoFlats1AndFilterStudio4Plus(String data1, String data2, String data3, String data4, String data5, String data6) throws JsonProcessingException {
//  1. Применить фильтр Комнатность - получить список id ЖК из/api/buildings/list_on_map?region_code[]=77&region_code[]=50&rooms[]=studio&no_flats=1
//  2. Если data.flats.offers не isEmpty(), то проверить, что есть offers.Key содержит нужный тип комнатности;
//  3. Иначе:
//  - получить из базы список properties. 202.values и проверить, что список содержит нужный тип комнатности (для Студий - 'Студии', 'Свободная планировка')
//  - если в списке properties. 202.values нет нужного типа квартиры,
//  - то получить все корпуса ЖК, собрать их properties. 202.values в список
//  - проверить, что хотя бы 1 значение из листа содержит нужный тип комнатности;
        List<Integer> buildingIdListWithGetParameterNoFlats1 = SearchBuildingsFiltersApi.getBuildingIdListWithFilterNoFlatsAndFilterRoom(1, data1);
        for (Integer id : buildingIdListWithGetParameterNoFlats1) {
            RootModel data = CardNovostroykiApiSteps.getBuildingData(id);
            if (!data.getData().getFlats().getOffers().isEmpty())
                Assertions.assertTrue(data.getData().getFlats().getOffers().stream().anyMatch(el -> el.getKey().equals(data2)));
            else {
                String buildingTypeRoomValues = BuildingService.selectProperties202Values(id);
                if (buildingTypeRoomValues == null || !buildingTypeRoomValues.contains(data3) && !buildingTypeRoomValues.contains(data4)
                        && !buildingTypeRoomValues.contains(data5) && !buildingTypeRoomValues.contains(data6)) {
                    List<Integer> houseIdList = BuildingDao.selectAllHouseId(id);
                    List<String> houseProperties202ValuesList = new ArrayList<>();
                    for (Integer houseId : houseIdList) {
                        houseProperties202ValuesList.add(BuildingService.selectProperties202Values(houseId));
                    }
                    Assertions.assertTrue(houseProperties202ValuesList.stream().anyMatch(el -> el.contains(data3) || el.contains(data4)
                            || el.contains(data5) || el.contains(data6)));
                }
            }
        }
    }

    @CsvSource(value = {"1, 1k, 1-комнатные", "2, 2k, 2-х комнатные", "3, 3k, 3-х комнатные"})
    @ParameterizedTest(name = "Проверить коррекность выдачи после применения фильтра {2} и гет параметра no_flats=1")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-646")
    void checkSearchWithGetParameterNoFlats1AndFilterRoom(String dataf, String data2, String data3) throws JsonProcessingException {
//  1. Получить список id ЖК из/api/buildings/list_on_map?rooms[]=Комнатность&no_flats=1
//  2. Если data.flats.offers не isEmpty(), то проверить, что есть offers.Key содержит нужный тип комнатности;
//  3. Иначе:
//  - получить из базы список properties. 202.values и проверить, что список содержит нужный тип комнатности (для Студий - 'Студии', 'Свободная планировка')
//  - если в списке properties. 202.values нет нужного типа квартиры,
//  - то получить все корпуса ЖК, собрать их properties. 202.values в список
//  - проверить, что хотя бы 1 значение из листа содержит нужный тип комнатности;
        List<Integer> buildingIdListWithGetParameterNoFlats1 = SearchBuildingsFiltersApi.getBuildingIdListWithFilterNoFlatsAndFilterRoom(1, dataf);
        for (Integer id : buildingIdListWithGetParameterNoFlats1) {
            RootModel data = CardNovostroykiApiSteps.getBuildingData(id);
            if (!data.getData().getFlats().getOffers().isEmpty())
                Assertions.assertTrue(data.getData().getFlats().getOffers().stream().anyMatch(el -> el.getKey().equals(data2)));
            else {
                String buildingTypeRoomValues = BuildingService.selectProperties202Values(id);
                if (buildingTypeRoomValues == null || !buildingTypeRoomValues.contains(data3)) {
                    List<Integer> houseIdList = BuildingDao.selectAllHouseId(id);
                    List<String> houseProperties202ValuesList = new ArrayList<>();
                    for (Integer houseId : houseIdList) {
                        houseProperties202ValuesList.add(BuildingService.selectProperties202Values(houseId));
                    }
                    Assertions.assertTrue(houseProperties202ValuesList.stream().anyMatch(el -> el.contains(data3)));
                }
            }
        }
    }

    @Test
    @DisplayName("Проверить коррекность выдачи после применения фильтра Цена От и Цена До и гет параметра no_flats=1")
    void checkSearchWithGetParameterNoFlats1AndFilterPrice() {
//  1. Получить список id ЖК из api/buildings/?price_min=value&price_max=value&no_flats=1
//  2. Получить число квартир для каждого ЖК;
//  3. Если число квартир > 0, то
//  - проверить, что количество предложений, у которых price_total>= priceMin and price_total <= priceMax больше 0;
//  4. Иначе:
//  - из апи ЖК получить и сохранить response
//  - создать Лист
//  - получить "flats": "price": "to" и "flats": "price": "from"
//  - если priceFrom==null&&priceTo!=null, то в Лист кладем 0, priceTo
//  - если priceFrom!=null&&priceTo==null, то в Лист кладем priceFrom priceMax
//  - иначе в Лист кладем priceFrom priceTo
//  5. Если !List.contains(null) и List.get(0) > priceMax || List.get(1) < priceMin, то
//  - необходимо получить все корпуса ЖК
//  - для каждого корпуса получить "flats": "price": "to" и "flats": "price": "from"
//  - по логике для род. жк сложить полученные значения в список;
//  6. Проверить, что в списке есть хотя бы 1 значение, у которого
//  - priceFrom <= priceMax && priceTo >= priceMin
        int priceMin = 1000000;
        int priceMax = 3000000;
        List<Integer> buildingIdListWithGetParameterNoFlats1 = SearchBuildingsFiltersApi
                .getBuildingIdListWithFilterNoFlatsAndFilterPrice(1, priceMin, priceMax);
        for (Integer id : buildingIdListWithGetParameterNoFlats1) {
            if (BuildingDao.selectCountAllFromFlatsWhereBuildingIdIsValueAndStatusIs1(id) > 0) {
                Assertions.assertTrue(BuildingDao.selectCountAllFromFlatsWhereBuildingIdIsValueAndStatusIs1WithFilterPrice(id, priceMin, priceMax) > 0);
            } else {
                RootModel root = CardNovostroykiApiSteps.getBuildingData(id);
                List<Long> priceList = new ArrayList<>();
                Long priceFrom = null;
                Long priceTo = null;
                try {
                    priceFrom = root.getData().getFlats().getPrice().getFrom();
                    priceTo = root.getData().getFlats().getPrice().getTo();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (priceFrom == null && priceTo != null) {
                    priceList.add(0L);
                    priceList.add(priceTo);
                } else if (priceFrom != null && priceTo == null) {
                    priceList.add(priceFrom);
                    priceList.add((long) priceMax);
                } else {
                    priceList.add(priceFrom);
                    priceList.add(priceTo);
                }
                if (!priceList.contains(null)) {
                    if (priceList.get(0) > priceMax || priceList.get(1) < priceMin) {
                        List<Integer> houseIdList = BuildingDao.selectAllHouseId(id);
                        List<List<Long>> totalPriceHouseList = new ArrayList<>();
                        for (Integer houseId : houseIdList) {
                            List<Long> priceHouseList = new ArrayList<>();
                            RootModel rootHouse = CardNovostroykiApiSteps.getBuildingData(houseId);
                            Long priceFromHouse = null;
                            Long priceToHouse = null;
                            try {
                                priceFromHouse = rootHouse.getData().getFlats().getPrice().getFrom();
                                priceToHouse = rootHouse.getData().getFlats().getPrice().getTo();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            if (priceFromHouse == null && priceToHouse != null) {
                                priceHouseList.add(0L);
                                priceHouseList.add(priceToHouse);
                            } else if (priceFromHouse != null && priceToHouse == null) {
                                priceHouseList.add(priceFromHouse);
                                priceHouseList.add((long) priceMax);
                            } else {
                                priceHouseList.add(priceFromHouse);
                                priceHouseList.add(priceToHouse);
                            }
                            totalPriceHouseList.add(priceHouseList);
                        }
                        Assertions.assertTrue(totalPriceHouseList.stream().filter(x -> x.get(0) != null || x.get(1) != null).anyMatch(el -> el.get(0) <= priceMax && el.get(1) >= priceMin));
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Проверить коррекность выдачи после применения фильтра Площадь От и Площадь До с гет параметром no_flats=1")
    void checkSearchWithGetParameterNoFlats1AndFilterArea() {
//        1. Получить список id ЖК из api/buildings/?square_min=VALUE&square_max=VALUE&no_flats=1
//        2. Получить число квартир для каждого ЖК;
//        3. Если число квартир > 0, то
//                - проверить, что количество предложений, у которых area_total>= priceMin and area_total <= priceMax больше 0;
//        4. Иначе:
//        - из апи ЖК получить массив square, закастить в Double, поместить в лист, отсортировать
//        - если if areaList.get(0) > squareMax || areaList.get(1) < squareMin, то
//                - получить все корпуса ЖК
//                - для каждого корпуса получить массив square, закастить в Double, поместить в лист, отсортировать, поместить в общий Лист
//        5. Проверить, что в общем Листе есть хотя бы 1 значение, у которого areaMin <= squareMax && areaMax >= squareMin
        int squareMin = 100;
        int squareMax = 200;
        List<Integer> buildingIdListWithGetParameterNoFlats1 =  List.of(17799);
//                SearchBuildingsFiltersApi.getBuildingIdListWithFilterNoFlatsAndFilterSquare(1, squareMin, squareMax);
        for (Integer id : buildingIdListWithGetParameterNoFlats1) {
            if (BuildingDao.selectCountAllFromFlatsWhereBuildingIdIsValueAndStatusIs1(id) > 0) {
                Assertions.assertTrue(BuildingDao.selectCountAllFromFlatsWhereBuildingIdIsValueAndStatusIs1WithFilterArea(id, squareMin, squareMax) > 0);
            } else {
                List<Double> areaList = CardNovostroykiApiSteps.getBuildingData(id).getData().getSquare().stream().map(Double::parseDouble).sorted().collect(Collectors.toList());
                System.out.println(areaList);
                if (areaList.get(0) > squareMax || areaList.get(1) < squareMin) {
                    List<Integer> houseIdList = BuildingDao.selectAllHouseId(id);
                    List<List<Double>> totalAreaHouseList = new ArrayList<>();
                    for (Integer houseId : houseIdList) {
                        try {
                            List<Double> areaHouseList = CardNovostroykiApiSteps.getBuildingData(houseId).getData().getSquare().stream().map(Double::parseDouble).sorted().collect(Collectors.toList());
                            totalAreaHouseList.add(areaHouseList);
                        }
                        catch (NullPointerException e){
                            e.printStackTrace();
                        }


                    }
                    System.out.println(totalAreaHouseList);
                    Assertions.assertTrue(totalAreaHouseList.stream().anyMatch(el -> el.get(0) <= squareMax && el.get(1) >= squareMin));
                }
            }
        }
    }
}
