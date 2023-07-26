package ru.dom_novo.api.tests.searchNovostroykiFiltersApiTests;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps.MarketcallBundleApi;
import ru.dom_novo.dataBase.dao.MarketcallBundleBuildingsDao;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Owner("PrudnikovaEkaterina")
@Tag("Api")
@Story("SearchNovostroykiFromMarketcallBundle")
@TmsLink("https://tracker.yandex.ru/NOVODEV-485")
public class MarketcallBundleApiTests {
    @Test
    @DisplayName("Проверить количество ЖК из marketcall-newbuilding-bundles в базе Ново")
    void checkCountBuildingsFromMarketcallBundles() {
        int countBuildingsExpected = MarketcallBundleApi.getCountBuildingsFromBundle();
        int countBuildingsActual = MarketcallBundleBuildingsDao.selectCountFromMarketcallBundleBuildings();
        assertThat(countBuildingsActual, is(countBuildingsExpected));
    }

    @Test
    @DisplayName("Проверить количество уникальных ЖК из marketcall-newbuilding-bundles в базе Ново")
    void checkCountDistinctBuildingsFromMarketcallBundles() {
        int countBuildingsExpected = MarketcallBundleApi.getCountDistinctBuildingsFromMarketcallBundles();
        int countBuildingsActual = MarketcallBundleBuildingsDao.selectCountDistinctBuildingsFromMarketcallBundleBuildings();
        assertThat(countBuildingsActual, is(countBuildingsExpected));
    }

    @Test
    @DisplayName("Проверить, что в базе Ново названия bundles соответствуют данным из marketcall.ru/api/v1/newbuilding-bundles")
    void checkTitleBundlesFromMarketcallApi() {
        List<String> titleListExpected = MarketcallBundleApi.getTitleBundles();
        List<String> titleListActual = MarketcallBundleBuildingsDao.selectTitlesFromMarketcallBundles();
        assertThat(titleListActual, is(titleListExpected));
    }

    @Test
    @DisplayName("Проверить, что в базе Ново id bundles соответствуют данным из marketcall.ru/api/v1/newbuilding-bundles")
    void checkIdBundlesFromMarketcallApi() {
        List<Integer> idListExpected = MarketcallBundleApi.getIdBundles();
        List<Integer> idListActual = MarketcallBundleBuildingsDao.selectExternalIdFromMarketcallBundles();
        assertThat(idListActual, is(idListExpected));
    }

}
