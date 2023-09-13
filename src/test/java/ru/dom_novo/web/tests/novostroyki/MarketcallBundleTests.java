package ru.dom_novo.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps.MarketcallBundleApiSteps;
import ru.dom_novo.dataBase.dao.MarketcallBundleBuildingsDao;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("SearchNovostroykiFromMarketcallBundle")
@TmsLink("https://tracker.yandex.ru/NOVODEV-485")
public class MarketcallBundleTests extends TestBase {
    NovostroykiPage novostroykiPage = new NovostroykiPage();

    @Test
    @DisplayName("Проверить, что количество ЖК на странице /novostroyki с фильтром bundle_ids соответсвует количеству ЖК из marketcall bundle с учетом региона")
    void checkSearchBuildingsFromMarketcallBundles() {
        List<Integer> idList = MarketcallBundleApiSteps.getIdBundles();
        for (Integer integer : idList) {
            open("/novostroyki?bundle_ids=" + integer + "&no_flats=3");
            sleep(1000);
//            для проверки, если тест падает
//            List<Integer> list = MarketcallBundleBuildingsDao.selectBuildingIdFromMarketcallBundles(integer);
//            List<Integer> list1 = given()
//                    .spec(requestSpec)
//                    .basePath("/api/buildings/")
//                    .param("per_page", 50)
//                    .param("page", 1)
//                    .param("region_code[]", 77)
//                    .param("region_code[]", 50)
//                    .param("bundle_ids[]", integer)
//                    .param("no_flats", 1)
//                    .get()
//                    .then()
//                    .extract().path("data.id");
//            for (Integer id : list) {
//                if (!list1.contains(id)) {
//                    System.out.println(id);
//                }
//            }
            int countBuildingsActual = novostroykiPage.getSearchNovostroykiContentTotal();
            int countBuildingsExpected = MarketcallBundleBuildingsDao.selectCountDistinctBuildingsFromMarketcallBundleBuildingsWhereExternalId(integer);
            assertThat(countBuildingsActual, is(countBuildingsExpected));
        }
    }

    @Test
    @DisplayName("Проверить тег на странице /novostroyki с фильтром bundle_ids")
    void checkSearchFiltersTagWithFilterBundleIds() {
        List<Integer> idList = MarketcallBundleApiSteps.getIdBundles();
        for (Integer integer : idList) {
            open("/novostroyki?bundle_ids=" + integer + "&no_flats=1");
            sleep(1000);
            String expected =MarketcallBundleBuildingsDao.selectTitleFromMarketcallBundlesForBundle(integer);
            String actual = novostroykiPage.getTextFromSearchFiltersTags();
            assertThat(actual, containsString(expected));
        }
    }
}
