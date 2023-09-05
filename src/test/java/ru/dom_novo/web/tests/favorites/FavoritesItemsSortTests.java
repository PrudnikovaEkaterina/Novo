package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.models.buildingModels.RootModel;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.dataBase.dao.FavoritesDao;
import ru.dom_novo.dataBase.dao.UsersDao;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.*;
import java.util.stream.Collectors;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesItemsSort")
public class FavoritesItemsSortTests extends TestBase {
    FavoritesPage favoritesPage = new FavoritesPage();
    String sort;

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по возрастанию на странице Мое избранное")
    void checkSortFavoritesPriceAsc() throws InterruptedException {
//      Получить id пользователя по номеру телефона;
//      Получить список избранных пользователем ЖК;
//      Для каждого ЖК получить data.price.from и положитт в  TreeMap, где ключ -цена, значение - title_eng ЖК;
//      Собрать все значения из TreeMap в список;
//      На странице Избранное применить сортировку  "Цена по возрастанию" и получить актальный список title_eng ЖК;
//      Сранить 2 списка на равенство;
        sort = "Цена по возрастанию";
        String phoneNumber = GenerationData.setRandomUserPhone();
        int userId = UsersDao.selectUserId(phoneNumber);
        List<Integer> favoritesBuildingId = FavoritesDao.selectBuildingIdFromFavorites(userId);
        Map<Long, String> map = new TreeMap<>();
        for(Integer buildingId:favoritesBuildingId){
            RootModel root = CardNovostroykiApiSteps.getBuildingData(buildingId);
            long priceFrom = root.getData().getFlats().getPrice().getFrom();
            String titleEng = root.getData().getTitleEng();
            map.put(priceFrom,titleEng);
        }
        List<String> listSortExpected = new ArrayList<>(map.values());
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        assertThat(listSortActual, is(listSortExpected));
    }
    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по убыванию' на странице Мое избранное")
    void checkSortFavoritesPriceDesc() throws InterruptedException {
//      Получить id пользователя по номеру телефона;
//      Получить список избранных пользователем ЖК;
//      Для каждого ЖК получить data.price.from и положитт в  HashMap, где ключ -цена, значение - title_eng ЖК;
//      Отсортировать ключи HashMap по убыванию. После этого собрать значения HashMap в список;
//      На странице Избранное применить сортировку  "Цена по убыванию" и получить актальный список title_eng ЖК;
//      Сранить 2 списка на равенство;

        sort = "Цена по убыванию";
        String phoneNumber = GenerationData.setRandomUserPhone();
        int userId = UsersDao.selectUserId(phoneNumber);
        List<Integer> favoritesBuildingId = FavoritesDao.selectBuildingIdFromFavorites(userId);
        Map<Long, String> map = new HashMap<>();
        for(Integer buildingId:favoritesBuildingId){
            RootModel root = CardNovostroykiApiSteps.getBuildingData(buildingId);
            long priceFrom = root.getData().getFlats().getPrice().getFrom();
            String titleEng = root.getData().getTitleEng();
            map.put(priceFrom,titleEng);
        }
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.<Long, String>comparingByKey().reversed()).map(Map.Entry::getValue).collect(Collectors.toList());
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Площадь по возрастанию' на странице Мое избранное")
    @Link("Тест падает для пользователя 79085040794, 79994817999 так как некоторые ЖК имеют одинаковую min(fl.area_total, в итоге выводятся в разной последовательности")
    void checkSortFavoritesAreaAsc() throws InterruptedException {
        sort = "Площадь по возрастанию";
        String phoneNumber = GenerationData.setRandomUserPhone();
        int userId = UsersDao.selectUserId(phoneNumber);
        List<Integer> favoritesBuildingId = FavoritesDao.selectBuildingIdFromFavorites(userId);
        Map<String, Double> map = new HashMap<>();
        for(Integer buildingId:favoritesBuildingId){
            RootModel root = CardNovostroykiApiSteps.getBuildingData(buildingId);
            double priceFrom=0;
            if (root.getData().getFlats().getSquareM2().getFrom()!=null) {
                 priceFrom = root.getData().getFlats().getSquareM2().getFrom();}
            else priceFrom = 0;
            String titleEng = root.getData().getTitleEng();
            map.put(titleEng, priceFrom);
        }
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        System.out.println(listSortExpected);
        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Площадь по убыванию' на странице Мое избранное")
    void checkSortFavoritesAreaDesc() throws InterruptedException {
        sort = "Площадь по убыванию";
        String phoneNumber = GenerationData.setRandomUserPhone();
        int userId = UsersDao.selectUserId(phoneNumber);
        List<Integer> favoritesBuildingId = FavoritesDao.selectBuildingIdFromFavorites(userId);
        Map<String, Double> map = new HashMap<>();
        for(Integer buildingId:favoritesBuildingId){
            RootModel root = CardNovostroykiApiSteps.getBuildingData(buildingId);
            double priceFrom=0;
            if (root.getData().getFlats().getSquareM2().getFrom()!=null) {
                priceFrom = root.getData().getFlats().getSquareM2().getFrom();}
            else priceFrom = 0;
            String titleEng = root.getData().getTitleEng();
            map.put(titleEng, priceFrom);
        }
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        System.out.println(listSortExpected);
        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена за м2 - по возрастанию' на странице Мое избранное")
    void checkSortFavoritesPriceM2Asc() throws InterruptedException {
        sort = "Цена за м2 - по возрастанию";
        String phoneNumber = GenerationData.setRandomUserPhone();
        int userId = UsersDao.selectUserId(phoneNumber);
        List<Integer> favoritesBuildingId = FavoritesDao.selectBuildingIdFromFavorites(userId);
        Map<String, Double> map = new HashMap<>();
        for(Integer buildingId:favoritesBuildingId){
            RootModel root = CardNovostroykiApiSteps.getBuildingData(buildingId);
            double priceFrom=0;
            if (root.getData().getFlats().getSquareM2().getFrom()!=null) {
                priceFrom = root.getData().getFlats().getPriceM2().getFrom();}
            else priceFrom = 0;
            String titleEng = root.getData().getTitleEng();
            map.put(titleEng, priceFrom);
        }
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        System.out.println(listSortExpected);
        assertThat(listSortActual, is(listSortExpected));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена за м2 - по убыванию' на странице Мое избранное")
    void checkSortFavoritesPriceM2Desc() throws InterruptedException {
        sort = "Цена за м2 - по убыванию";
        String phoneNumber = GenerationData.setRandomUserPhone();
        int userId = UsersDao.selectUserId(phoneNumber);
        List<Integer> favoritesBuildingId = FavoritesDao.selectBuildingIdFromFavorites(userId);
        Map<String, Double> map = new HashMap<>();
        for(Integer buildingId:favoritesBuildingId){
            RootModel root = CardNovostroykiApiSteps.getBuildingData(buildingId);
            double priceFrom=0;
            if (root.getData().getFlats().getSquareM2().getFrom()!=null) {
                priceFrom = root.getData().getFlats().getPriceM2().getFrom();}
            else priceFrom = 0;
            String titleEng = root.getData().getTitleEng();
            map.put(titleEng, priceFrom);
        }
        List<String> listSortExpected = map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());
        favoritesPage
                .openMePageWithApiAuth(phoneNumber)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort);
        List<String> listSortActual = favoritesPage.getBuildingsTitleEng();
        System.out.println(listSortExpected);
        assertThat(listSortActual, is(listSortExpected));
    }

}
