package ru.dom_novo.web.tests.novostroyki;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.enumsWeb.RoomEnum;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.pages.components.FooterComponent;
import ru.dom_novo.web.pages.components.SearchNovostroykiFiltersComponent;
import ru.dom_novo.web.tests.TestBase;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.github.tomakehurst.wiremock.client.WireMock.*;


@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("SearchFilters")
@WireMockTest(httpPort = 8089)
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
        String wireUrl = "http://127.0.0.1:8080";

        stubFor(WireMock.get("/api/location/find_address")
                .withQueryParam("term", matching(".*"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withBody("!!! Service Unavailable !!!"))
        );
        open(wireUrl+"/api/location/find_address?term=%D0%BB%D1%83%D1%87%D0%B8");
//        String searchBuildingName = GenerationData.setRandomBuilding();
//        searchFilters
//                .setValueInGeoSearchFilter(searchBuildingName)
//                .selectDropdownItem(searchBuildingName);
//        novostroykiPage.verifySearchBuildingTitleText(searchBuildingName);

        while (true){}

    }

    @Test
    @DisplayName("Проверить результаты поиска ЖК по району на странице /novostroyki")
    void searchDistrict() {
        String searchDistrictName = "Арбат";
        searchFilters
                .setValueInGeoSearchFilter(searchDistrictName)
                .selectDropdownDistrict(searchDistrictName);
        novostroykiPage.verifyResultSearchBuildingContent(searchDistrictName);
    }

    @Test
    @DisplayName("Проверить результаты поиска ЖК по городу на странице /novostroyki")
    void searchCity() {
        String searchCityName = GenerationData.setRandomCity();
        searchFilters
                .setValueInGeoSearchFilter(searchCityName)
                .selectDropdownCity(searchCityName);
        novostroykiPage.verifyResultSearchBuildingContent(searchCityName);
    }


    @Test
    @DisplayName("Проверить результаты поиска ЖК по округу на странице /novostroyki")
    void searchCounty() {
        String searchCountyName = "Восточный административный округ";
        String verifyCountyName = "ВАО";
        searchFilters
                .setValueInGeoSearchFilter(searchCountyName)
                .selectDropdownItem(searchCountyName);
        novostroykiPage.verifyResultSearchBuildingContent(verifyCountyName);
    }

    @Test
    @DisplayName("Проверить результаты поиска ЖК по застройщику на странице /novostroyki")
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
