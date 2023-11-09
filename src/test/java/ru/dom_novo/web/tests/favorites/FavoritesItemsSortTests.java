package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.buildingsOnMapSteps.BuildingsOnMapSteps;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApiSteps;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesItemsSort")
public class FavoritesItemsSortTests extends TestBase {

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по возрастанию на странице Мое избранное")
    void checkSortFavoritesPriceAsc() throws InterruptedException {
        FavoritesPage favoritesPage = new FavoritesPage();
        String sort = "Цена по возрастанию";

        String phoneNumber = GenerationData.setRandomUserPhone();
        Map<Integer, Integer> map = BuildingsOnMapSteps.createMapFromRootBuildingOnMapModel(phoneNumber);
        List<Integer> list = BuildingsOnMapSteps.sortMapByKeyAscAndReturnValue(map);
        List<String> sortExpectedList = list.stream().map(BuildingDao::selectTitleEngFromBuildings).collect(Collectors.toList());

        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        sleep(1000);
        List<String> sortActualList = favoritesPage.getBuildingsTitleEng();

        assertThat(sortActualList, is(sortExpectedList));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по убыванию' на странице Мое избранное")
    void checkSortFavoritesPriceDesc() throws InterruptedException {
        FavoritesPage favoritesPage = new FavoritesPage();
        String sort  = "Цена по убыванию";

        String phoneNumber = GenerationData.setRandomUserPhone();
        Map<Integer, Integer> map = BuildingsOnMapSteps.createMapFromRootBuildingOnMapModel(phoneNumber);
        List<Integer> list = BuildingsOnMapSteps.sortMapByKeyDescAndReturnBuildingId(map);
        List<String> sortExpectedList = list.stream().map(BuildingDao::selectTitleEngFromBuildings).collect(Collectors.toList());

        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        sleep(2000);
        List<String> sortActualList = favoritesPage.getBuildingsTitleEng();

        assertThat(sortActualList, is(sortExpectedList));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Площадь по возрастанию' на странице Мое избранное")
    @Link("Тест падает для пользователя 79085040794, 79994817999 так как некоторые ЖК имеют одинаковую min(fl.area_total, в итоге выводятся в разной последовательности")
    void checkSortFavoritesAreaAsc() throws InterruptedException {
        FavoritesPage favoritesPage = new FavoritesPage();
        String sort = "Площадь по возрастанию";

        String phoneNumber = GenerationData.setRandomUserPhone();
        List<Integer> buildingIdList = UserFavoritesApiSteps.getUserFavoritesBuilding(phoneNumber);
        Map<String, Double> map = CardNovostroykiApiSteps.createMapTitleEngAndSquareM2(buildingIdList);
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());

        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        sleep(2000);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();

        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Площадь по убыванию' на странице Мое избранное")
    void checkSortFavoritesAreaDesc() throws InterruptedException {
        FavoritesPage favoritesPage = new FavoritesPage();
        String sort = "Площадь по убыванию";

        String phoneNumber = "70003423423";
                GenerationData.setRandomUserPhone();
        List<Integer> buildingIdList = UserFavoritesApiSteps.getUserFavoritesBuilding(phoneNumber);
        Map<String, Double> map = CardNovostroykiApiSteps.createMapTitleEngAndSquareM2(buildingIdList);
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());

        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        sleep(2000);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();

        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена за м2 - по возрастанию' на странице Мое избранное")
    void checkSortFavoritesPriceM2Asc() throws InterruptedException {
        FavoritesPage favoritesPage = new FavoritesPage();
        String sort = "Цена за м2 - по возрастанию";

        String phoneNumber = GenerationData.setRandomUserPhone();
        List<Integer> buildingIdList = UserFavoritesApiSteps.getUserFavoritesBuilding(phoneNumber);
        Map<String, Double> map = CardNovostroykiApiSteps.createMapTitleEngAndPriceM2(buildingIdList);
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        sleep(2000);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();

        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена за м2 - по убыванию' на странице Мое избранное")
    void checkSortFavoritesPriceM2Desc() throws InterruptedException {
        FavoritesPage favoritesPage = new FavoritesPage();
        String sort = "Цена за м2 - по убыванию";

        String phoneNumber = GenerationData.setRandomUserPhone();
        List<Integer> buildingIdList = UserFavoritesApiSteps.getUserFavoritesBuilding(phoneNumber);
        Map<String, Double> map = CardNovostroykiApiSteps.createMapTitleEngAndPriceM2(buildingIdList);
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());

        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        sleep(2000);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();

        assertThat(listSortActual, is(listSortExpected));
    }

}
