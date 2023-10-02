package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApiSteps;
import ru.dom_novo.api.steps.meApiSteps.MeApiSteps;
import ru.dom_novo.dataBase.dao.FavoritesDao;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesItemsDateAdd")
public class FavoritesItemsDateTests extends TestBase {
    FavoritesPage favoritesPage = new FavoritesPage();
    String phoneNumber = GenerationData.setRandomUserPhone();

    @BeforeEach
    void beforeEach() throws InterruptedException {
        UserFavoritesApiSteps.addBuildingToUserFavorites(phoneNumber);
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle();
    }

    @Test
    @DisplayName("Проверить дату добавления ЖК на странице Мое избранное")
    void checkFavoritesBuildingsDateAdd() {
        int userId = MeApiSteps.getUserId(phoneNumber);
        Map<Integer, String> map = FavoritesDao.select(userId);
        Map<Integer, String> collect = map.entrySet()
                .stream()
                .collect(toMap(el -> CardNovostroykiApiSteps.getPriceFrom(el.getKey()), Map.Entry::getValue));
        TreeMap<Integer, String> collectSort = new TreeMap<>(collect);
        List<String> buildingsDateAddExpected = new ArrayList<>(collectSort.values());
        List<String> buildingsDateAddActual = favoritesPage.getBuildingsDateText();
        assertThat(buildingsDateAddActual, is(buildingsDateAddExpected));
    }
}
