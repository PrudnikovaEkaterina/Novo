package ru.prudnikova.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.prudnikova.test_data.GenerationData;
import ru.prudnikova.web.enums.BuildingEnum;
import ru.prudnikova.web.enums.CityEnum;
import ru.prudnikova.web.enums.DeveloperEnum;
import ru.prudnikova.web.enums.RoomEnum;
import ru.prudnikova.web.pages.NovostroykiPage;
import ru.prudnikova.web.pages.components.Footer;
import ru.prudnikova.web.pages.components.SearchNovostroykiFilters;
import ru.prudnikova.web.tests.TestBase;

import static com.codeborne.selenide.Selenide.open;

@Tag("Web")
@Story("SearchFilters")

public class SearchNovostroykiFiltersTests extends TestBase {

    NovostroykiPage novostroykiPage = new NovostroykiPage();
    SearchNovostroykiFilters searchFilters = new SearchNovostroykiFilters();
    Footer footer = new Footer();

    @BeforeEach
    void beforeEach() {
        open("https://novo-dom.ru/novostroyki");
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Проверить результаты поиска по ЖК на странице /novostroyki")
    void searchNovostroyka() {
        String searchBuildingName = GenerationData.setRandomBuilding(BuildingEnum.values());
        searchFilters.setValueInGeoSearchFilter(searchBuildingName)
                .selectDropdownItem(searchBuildingName);
        novostroykiPage.verifySearchBuildingTitleText(searchBuildingName);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Проверить результаты поиска ЖК по району на странице /novostroyki")
    void searchDistrict() {
        String searchDistrictName = "Арбат";
        searchFilters.setValueInGeoSearchFilter(searchDistrictName)
                .selectDropdownDistrict(searchDistrictName);
        novostroykiPage.verifyResultSearchBuildingContent(searchDistrictName);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Проверить результаты поиска ЖК по городу на странице /novostroyki")
    void searchCity() {
        String searchCityName = GenerationData.setRandomCity(CityEnum.values());
        searchFilters.setValueInGeoSearchFilter(searchCityName)
                .selectDropdownCity(searchCityName);
        novostroykiPage.verifyResultSearchBuildingContent(searchCityName);
    }


    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Проверить результаты поиска ЖК по округу на странице /novostroyki")
    void searchСounty() {
        String searchCountyName = "Восточный административный округ";
        String verifyCountyName = "ВАО";
        searchFilters.setValueInGeoSearchFilter(searchCountyName)
                .selectDropdownItem(searchCountyName);
        novostroykiPage.verifyResultSearchBuildingContent(verifyCountyName);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Проверить результаты поиска ЖК по застройщику на странице /novostroyki")
    void searchDeveloper() {
        String searchDeveloperName = GenerationData.setRandomDeveloper(DeveloperEnum.values());
        searchFilters.setValueInGeoSearchFilter(searchDeveloperName)
                .selectDropdownItem(searchDeveloperName);
        novostroykiPage.verifyResultSearchBuildingDeveloper(searchDeveloperName);
    }


    @ParameterizedTest(name = "Проверить выдачу ЖК после примения фильтра 'Комнатность' {0} на странице /novostroyki")
    @EnumSource(RoomEnum.class)
    @Owner("PrudnikovaEkaterina")
    void setFilterRoomsAndVerifyResultSearch(RoomEnum roomEnum) {
        String rooms = roomEnum.name;
        searchFilters.clickCheckboxFilterRooms(rooms);
        novostroykiPage.verifyResultSearchByFilterRooms(rooms);
    }

    @CsvSource(value = {"15000000, От 15 млн. ₽", "100, От 100 ₽"})
    @ParameterizedTest(name = "Ввести значение {0} в фильтр 'Цена от' и проверить появление тега {1}")
    @Owner("PrudnikovaEkaterina")
    void setPriceFromFilter(String data1, String data2) {
        searchFilters.setPriceFrom(data1);
        novostroykiPage.verifyTagVisible(data2);
    }

    @CsvSource(value = {"1000000, До 1 млн. ₽", "8000000, До 8 млн. ₽"})
    @ParameterizedTest(name = "Ввести значение {0} в фильтр 'Цена до' и проверить появление тега {1}")
    @Owner("PrudnikovaEkaterina")
    void setPriceToFilter(String data1, String data2) {
        searchFilters.setPriceTo(data1);
        novostroykiPage.verifyTagVisible(data2);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Проскроллить новостройки до последней страницы и проверить отображение футера")
    void scrollNovostroykiItemsToLastPage() {
        novostroykiPage.scrollNovostroykiItemsToLastPage();
        footer.verifyFooterMenuHeader();
    }

}
