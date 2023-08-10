package ru.dom_novo.web.tests.favorites;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.meApiSteps.MeApiSteps;
import ru.dom_novo.dataBase.dao.FavoritesDao;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesItemsSort")
public class FavoritesItemsSortTests extends TestBase {

    FavoritesPage favoritesPage = new FavoritesPage();

    String phoneNumber = GenerationData.setRandomUserPhone();
    String sort;

    @BeforeEach
    void beforeEach() throws InterruptedException {
        Selenide.sleep(5000);
//        UserFavoritesApi.addBuildingToUserFavorites(phoneNumber);
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle();
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по возрастанию на странице Мое избранное")
    void checkSortFavoritesPriceAsc() {
        sort = "Цена по возрастанию";
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<String> listSortExpected = FavoritesDao.selectFavoritesBuildingsSortPriceAsc(userId);
        favoritesPage.setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        assertThat(listSortActual, is(listSortExpected));
    }
    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по убыванию' на странице Мое избранное")
    void checkSortFavoritesPriceDesc() {
        sort = "Цена по убыванию";
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<String> listSortExpected = FavoritesDao.selectFavoritesBuildingsSortPriceDesc(userId);
        favoritesPage.setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
         assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Площадь по возрастанию' на странице Мое избранное")
    @Link("Тест падает для пользователя 79085040794, 79994817999 так как некоторые ЖК имеют одинаковую min(fl.area_total, в итоге выводятся в разной последовательности")
    void checkSortFavoritesAreaAsc() {
        sort = "Площадь по возрастанию";
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<String> listSortExpected = FavoritesDao.selectFavoritesBuildingsSortAreaAsc(userId);
        System.out.println(listSortExpected);
        favoritesPage.setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        System.out.println(listSortActual);
        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Площадь по убыванию' на странице Мое избранное")
    void checkSortFavoritesAreaDesc() {
        sort = "Площадь по убыванию";
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<String> listSortExpected = FavoritesDao.selectFavoritesBuildingsSortAreaDesc(userId);
        favoritesPage.setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена за м2 - по возрастанию' на странице Мое избранное")
    void checkSortFavoritesPriceM2Asc() {
        sort = "Цена за м2 - по возрастанию";
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<String> listSortExpected = FavoritesDao.selectFavoritesBuildingsSortPriceM2Asc(userId);
        favoritesPage.setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
         assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена за м2 - по убыванию' на странице Мое избранное")
    void checkSortFavoritesPriceM2Desc() {
        sort = "Цена за м2 - по убыванию";
        int userId = MeApiSteps.getUserId(phoneNumber);
        List<String> listSortExpected = FavoritesDao.selectFavoritesBuildingsSortPriceM2Desc(userId);
        favoritesPage.setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
         assertThat(listSortActual, is(listSortExpected));
    }
}
