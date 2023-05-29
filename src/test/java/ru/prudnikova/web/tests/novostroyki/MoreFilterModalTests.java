package ru.prudnikova.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.test_data.GenerationData;
import ru.prudnikova.web.enums.HousingClassEnum;
import ru.prudnikova.web.pages.NovostroykiPage;
import ru.prudnikova.web.pages.components.SearchNovostroykiFilters;
import ru.prudnikova.web.tests.TestBase;

@Tag("Web")
@Story("MoreFilterModal")
public class MoreFilterModalTests extends TestBase {

    NovostroykiPage novostroykiPage = new NovostroykiPage();
    SearchNovostroykiFilters searchFilters = new SearchNovostroykiFilters();

    @BeforeEach
    void beforeEach() {
        novostroykiPage.openNovostroykiPage();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Проверить открытие модального окна по клику на кнопку Все фильтры странице /novostroyki")
    void checkOpenMoreFiltersModal() {
        novostroykiPage.openMoreFiltersModal();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Проверить выдачу на странице /novostroyki  после примения фильтра Класс жилья")
    void clickCheckboxHousingClassAndVerifyResultSearch() {
        String housingClass = GenerationData.setRandomHousingClass(HousingClassEnum.values());
        String numberOfFiltersSelected = "1";
        novostroykiPage.openMoreFiltersModal().clickCheckboxHousingClass(housingClass).clickShowButton();
        novostroykiPage.verifyResultSearchByFilterHousingClass(housingClass + " класс");
        novostroykiPage.verifyNotificationIndicator(numberOfFiltersSelected);
    }

}
