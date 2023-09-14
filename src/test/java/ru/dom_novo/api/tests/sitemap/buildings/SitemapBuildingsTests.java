package ru.dom_novo.api.tests.sitemap.buildings;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.sitemapSteps.SitemapBuildingsSteps;
import ru.dom_novo.dataBase.dao.BuildingDao;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tag("Api")
@Owner("PrudnikovaEkaterina")
@TmsLink("https://tracker.yandex.ru/NOVODEV-686")
@Disabled
public class SitemapBuildingsTests {
    @Test
    @Description("Проверить, что роут api/sitemap/xml/buildings отдает корректный список ЖК")
    void checkBuildings() {
//        1. Получить список уникальных title_eng для ЖК с предложениями из таблицы buildings;
//        2. Из роута api/sitemap/xml/buildings?limit=300 собрать все значения slug в список;
//        3. Отсортировать оба списка в лексикографическом порядке;
//        4. Сравнить 2 списка на идентичность;
        List<String> buildingTitleEngListExpected = BuildingDao.selectDistinctBuildingTitleEng();
        List<String> buildingTitleEngListActual = SitemapBuildingsSteps.getSlugList();
        Collections.sort(buildingTitleEngListExpected);
        Collections.sort(buildingTitleEngListActual);
        assertThat(buildingTitleEngListActual, is(buildingTitleEngListExpected));
    }
}
