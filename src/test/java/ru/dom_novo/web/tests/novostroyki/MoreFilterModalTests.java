package ru.dom_novo.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.tests.TestBase;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("MoreFilterModal")
public class MoreFilterModalTests extends TestBase {

    NovostroykiPage novostroykiPage = new NovostroykiPage();

    @BeforeEach
    void beforeEach() {
        novostroykiPage.openNovostroykiPage();
    }

    @Test
    @DisplayName("Проверить открытие модального окна по клику на кнопку Все фильтры странице /novostroyki")
    void checkOpenMoreFiltersModal() {
        novostroykiPage.openMoreFiltersModal();
    }

    @Test
    @DisplayName("Проверить выдачу на странице /novostroyki  после примения фильтра Класс жилья")
    void clickCheckboxHousingClassAndVerifyResultSearch() {
        String housingClass = GenerationData.setRandomHousingClass();
        String numberOfFiltersSelected = "1";
        novostroykiPage
                .openMoreFiltersModal()
                .clickCheckboxHousingClass(housingClass)
                .clickShowButton();
        novostroykiPage.verifyResultSearchByFilterHousingClass(housingClass + " класс");
        novostroykiPage.verifyNotificationIndicator(numberOfFiltersSelected);
    }

}
