package ru.dom_novo.web.tests.novostroyki;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps.SearchBuildingsFiltersApi;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.enumsWeb.RoomEnum;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.pages.components.FooterComponent;
import ru.dom_novo.web.pages.components.SearchNovostroykiFiltersComponent;
import ru.dom_novo.web.tests.TestBase;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("SearchFilters")
public class SearchNovostroykiFiltersTests extends TestBase {
    NovostroykiPage novostroykiPage = new NovostroykiPage();
    SearchNovostroykiFiltersComponent searchFilters = new SearchNovostroykiFiltersComponent();
    FooterComponent footer = new FooterComponent();

    @BeforeEach
    void beforeEach() {
        novostroykiPage.openNovostroykiPage();
    }

    @Test
    @DisplayName("Проверить результаты поиска по ЖК на странице /novostroyki")
    void searchNovostroyka() {
        String searchBuildingName = GenerationData.setRandomBuilding();
        searchFilters
                .setValueInGeoSearchFilter(searchBuildingName)
                .selectDropdownItem(searchBuildingName);
        novostroykiPage.verifySearchBuildingTitleText(searchBuildingName);
    }

    @Test
    @DisplayName("Проверить результаты поиска по району на странице /novostroyki")
    void searchDistrict() {
        String searchDistrictName = "Арбат";
        searchFilters
                .setValueInGeoSearchFilter(searchDistrictName)
                .selectDropdownDistrict(searchDistrictName);
        novostroykiPage.verifyResultSearchBuildingContent(searchDistrictName);
    }

    @Test
    @DisplayName("Проверить результаты поиска по городу на странице /novostroyki")
    void searchCity() {
        String searchCityName = GenerationData.setRandomCity();
        searchFilters
                .setValueInGeoSearchFilter(searchCityName)
                .selectDropdownCity(searchCityName);
        novostroykiPage.verifyResultSearchBuildingContent(searchCityName);
    }


    @Test
    @DisplayName("Проверить результаты поиска по округу на странице /novostroyki")
    void searchCounty() {
        String searchCountyName = "Восточный административный округ";
        String verifyCountyName = "ВАО";
        searchFilters
                .setValueInGeoSearchFilter(searchCountyName)
                .selectDropdownItem(searchCountyName);
        novostroykiPage.verifyResultSearchBuildingContent(verifyCountyName);
    }

    @Test
    @DisplayName("Проверить результаты поиска по застройщику на странице /novostroyki")
    void searchDeveloper() {
        String searchDeveloperName = GenerationData.setRandomDeveloper();
        searchFilters
                .setValueInGeoSearchFilter(searchDeveloperName)
                .selectDropdownItem(searchDeveloperName);
        novostroykiPage.verifyResultSearchBuildingDeveloper(searchDeveloperName);
    }


    @ParameterizedTest(name = "Проверить выдачу ЖК после примения фильтра 'Комнатность' {0} на странице /novostroyki")
    @EnumSource(RoomEnum.class)
    void setFilterRoomsAndVerifyResultSearch(RoomEnum roomEnum) {
        String rooms = roomEnum.name;
        searchFilters.clickCheckboxFilterRooms(rooms);
        sleep(1000);
        novostroykiPage.verifyResultSearchByFilterRooms(rooms);
    }

    @CsvSource(value = {"15000000, От 15 млн. ₽", "100, От 100 ₽"})
    @ParameterizedTest(name = "Ввести значение {0} в фильтр 'Цена от' и проверить появление тега {1}")
    void setPriceFromFilter(String data1, String data2) {
        searchFilters.setPriceFrom(data1);
        novostroykiPage.verifyTagVisible(data2);
    }

    @CsvSource(value = {"1000000, До 1 млн. ₽", "8000000, До 8 млн. ₽"})
    @ParameterizedTest(name = "Ввести значение {0} в фильтр 'Цена до' и проверить появление тега {1}")
    void setPriceToFilter(String data1, String data2) {
        searchFilters.setPriceTo(data1);
        novostroykiPage.verifyTagVisible(data2);
    }

    @Test
    @DisplayName("Проскроллить новостройки до последней страницы и проверить отображение футера")
    void scrollNovostroykiItemsToLastPage() {
        novostroykiPage.scrollNovostroykiItemsToLastPage();
        footer.verifyFooterMenuHeader();
    }
    @Test
    @DisplayName("https://tracker.yandex.ru/NOVODEV-646")
    void checkSearchWithGetParameterNoFlats1() {
//        no_flats=1 - Режим для брокеров МК. Поиск осуществляется по предложениям ТА и данным из админки Move.
//        ЖК без предложений ТА или цен в админке не выводятся. Это требуемый дополнительный режим.
//        Особенность сортировки для no_flats=1: при применении сортировки по цене или площади (всё, кроме популярности),
//        выдача состоит из 2-х частей, отсортированных независимо друг от друга. В начале идут отсортированные ЖК с предложениями ТА,
//        затем отсортированные по данным админки (кроме тех ЖК, для которых были предложения ТА).
//  1. Получить список id ЖК из /api/buildings/list_on_map?region_code[]=77&region_code[]=50&no_flats=1
//  2. Получить из базы список id ЖК с предложениями
//  3. Получить из базы список id ЖК без предложений, но с ценами из новой админки;
//  4. Объединить списки из бд в один и проверить, что его размер равен размеру списку из апи;
//  5. Отсортировать 2 списка и проверить на равенство;
       List<Integer> buildingIdListWithGetParameterNoFlats1 = SearchBuildingsFiltersApi.getBuildingIdListWithFilterNoFlats(1);
        List<Integer> buildingIdListWithFlats = BuildingDao.selectDistinctBuildingIdWithFlats();
        List<Integer> buildingIdListWithPrices =  BuildingDao.selectDistinctBuildingIdWithPrices();
        List<Integer> totalBuildingIdList = new ArrayList<>();
        totalBuildingIdList.addAll(buildingIdListWithFlats);
        totalBuildingIdList.addAll(buildingIdListWithPrices);
        Collections.sort(buildingIdListWithGetParameterNoFlats1);
        Collections.sort(totalBuildingIdList);
        assertThat(buildingIdListWithGetParameterNoFlats1, is(totalBuildingIdList));
    }
    @Test
    @DisplayName("https://tracker.yandex.ru/NOVODEV-646")
    void checkSearchWithGetParameterNoFlats1AndFilterRoom() throws JsonProcessingException {
//  1. Применить фильтр Комнатность - получить список id ЖК из/api/buildings/list_on_map?region_code[]=77&region_code[]=50&rooms[]=studio&no_flats=1
//  2. Проверить, что в цикле, что
//  - в /api/buildings/id data.flats.offers.(комнатность).total !=null
//  - если =null, то
//  - получить из базы список properties. 202.values и проверить, что список содержит нужный тип комнатности (для Студий - 'Студии', 'Свободная планировка')
//  - если в списке properties. 202.values нет нужного типа квартиры,
//  - то получить все корпуса ЖК, собрать их properties. 202.values в список
//  - проверить, что хотя бы 1 значение из листа равно 'Студии' или'Свободная планировка' (подставить нужный тип комнатности);
        String expected = "Студии";
        String expected1 = "Свободная планировка";
        List<Integer> buildingIdListWithGetParameterNoFlats1 = SearchBuildingsFiltersApi.getBuildingIdListWithFilterNoFlatsAndFilterRoom(1, "studio");
        for(Integer id:buildingIdListWithGetParameterNoFlats1){
            if(CardNovostroykiApiSteps.getBuildingData(id).getData().getFlats().getTotal()!=null){
                System.out.println(CardNovostroykiApiSteps.getBuildingData(id).getData().getFlats().getTotal());
                Assertions.assertTrue(CardNovostroykiApiSteps.getBuildingData(id).getData().getFlats()
                        .getOffers().stream().anyMatch(el->el.getKey().equals("studio")));
                CardNovostroykiApiSteps.getBuildingData(id).getData().getFlats()
                        .getOffers().forEach(el-> System.out.println(el.getKey()));}
            else {
//                if(
//                        BuildingDao.selectProperties202Values(id)==null|| Objects.requireNonNull(BuildingDao.selectProperties202Values(id))
//                        .stream().noneMatch(el->el.equals(expected)||el.equals(expected1))
//                )
                {
                    System.out.println(BuildingDao.selectProperties202Values(id));
                    List<Integer> houseIdList = BuildingDao.selectDistinctHouseId(id);
                    System.out.println(houseIdList);
                    List<String> houseProperties202ValuesList = new ArrayList<>();
                    for (Integer houseId:houseIdList){
                       houseProperties202ValuesList.add(String.valueOf(BuildingDao.selectProperties202Values(houseId)));
                    }
                    System.out.println(houseProperties202ValuesList);
                    Assertions.assertTrue(houseProperties202ValuesList.stream().anyMatch(el->el.contains(expected)||el.contains(expected1)));
                }
                }
            }
        }
//        List<Integer> buildingIdListWithFlats = BuildingDao.selectDistinctBuildingIdWithFlats();
//        List<Integer> buildingIdListWithPrices =  BuildingDao.selectDistinctBuildingIdWithPrices();
//        List<Integer> totalBuildingIdList = new ArrayList<>();
//        totalBuildingIdList.addAll(buildingIdListWithFlats);
//        totalBuildingIdList.addAll(buildingIdListWithPrices);
//        Collections.sort(buildingIdListWithGetParameterNoFlats1);
//        Collections.sort(totalBuildingIdList);
//        assertThat(buildingIdListWithGetParameterNoFlats1, is(totalBuildingIdList));
    }

