package ru.dom_novo.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

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
                .selectCheckboxHousingClass(housingClass)
                .clickShowButton();
        novostroykiPage.verifyResultSearchByFilterHousingClass(housingClass + " класс");
        novostroykiPage.verifyNotificationIndicator(numberOfFiltersSelected);
    }
}


