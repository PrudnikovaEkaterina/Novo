package ru.dom_novo.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps.MarketcallBundleApi;
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
        List<Integer> idList = MarketcallBundleApi.getIdBundles();
        for (Integer integer : idList) {
            open("/novostroyki?bundle_ids=" + integer + "&no_flats=1");
            sleep(1000);
            int countBuildingsActual = novostroykiPage.getSearchNovostroykiContentTotal();
            int countBuildingsExpected = MarketcallBundleBuildingsDao.selectCountDistinctBuildingsFromMarketcallBundleBuildingsWhereExternalId(integer);
            assertThat(countBuildingsActual, is(countBuildingsExpected));
        }
    }

    @Test
    @DisplayName("Проверить тег на странице /novostroyki с фильтром bundle_ids")
    void checkSearchFiltersTagWithFilterBundleIds() {
        List<Integer> idList = MarketcallBundleApi.getIdBundles();
        for (Integer integer : idList) {
            open("/novostroyki?bundle_ids=" + integer + "&no_flats=1");
            sleep(1000);
            String expected =MarketcallBundleBuildingsDao.selectTitleFromMarketcallBundlesForBundle(integer);
            String actual = novostroykiPage.getTextFromSearchFiltersTags();
            assertThat(actual, containsString(expected));
        }
    }
}
