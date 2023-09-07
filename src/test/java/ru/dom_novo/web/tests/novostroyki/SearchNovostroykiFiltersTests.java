package ru.dom_novo.web.tests.novostroyki;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.dom_novo.api.models.buildingModels.RootModel;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps.SearchBuildingsFiltersApi;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.dataBase.services.BuildingService;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.enumsWeb.RoomEnum;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.pages.components.FooterComponent;
import ru.dom_novo.web.pages.components.SearchNovostroykiFiltersComponent;
import ru.dom_novo.web.tests.TestBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;

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

}

