package ru.dom_novo.web.tests.favorites;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;
import ru.dom_novo.api.steps.favoritesApiSteps.UserFavoritesApiSteps;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.web.pages.FavoritesPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FavoritesItemsSort")
public class FavoritesItemsSortTests extends TestBase {

    // Сделала через Response loginResponse, чтобы избежать лишних запросов к АПИ и 429 ошибки соответсвенно;

    String phoneNumber = GenerationData.setRandomUserPhone();
    Response loginResponse = AuthApiSteps.getLoginResponse(phoneNumber);
    String accessToken = AuthApiSteps.getAccessTokenUseLoginResponse(loginResponse);

    private static List<String> getSortActualList (String sort, Response loginResponse) throws InterruptedException {
        FavoritesPage favoritesPage = new FavoritesPage();
        return favoritesPage
                .openFavoritesPageWithAuthUseLoginResponse(loginResponse)
                .checkFavoritesHeaderTitle()
                .setSortFavoritesBuildings(sort)
                .collectBuildingsTitleEng();
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по возрастанию' на странице Мое избранное")
//   По коду так - если у юзера нет Избранных ЖК - возвращаются ЖК из поиска,
//   в дом дереве они тоже есть, но not visible
    void checkSortFavoritesPriceAsc() throws InterruptedException {
        String sort = "Цена по возрастанию";
        UserFavoritesApiSteps.addBuildingToUserFavoritesUseAccessToken(accessToken);
        Map<Long, String> priceFromAndTitleEngMap = UserFavoritesApiSteps.getUserFavoritesBuildingsPriceFromAndTitleEngMap(accessToken);
        List<String> sortExpectedList = UserFavoritesApiSteps.sortMapByKeyAscAndReturnValue(priceFromAndTitleEngMap);
        List<String> sortActualList = getSortActualList(sort, loginResponse);
        assertThat(sortActualList, is(sortExpectedList));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена по убыванию' на странице Мое избранное")
    void checkSortFavoritesPriceDesc() throws InterruptedException {
        String sort  = "Цена по убыванию";
        UserFavoritesApiSteps.addBuildingToUserFavoritesUseAccessToken(accessToken);
        Map<Long, String> priceFromAndTitleEngMap = UserFavoritesApiSteps.getUserFavoritesBuildingsPriceFromAndTitleEngMap(accessToken);
        List<String> sortExpectedList = UserFavoritesApiSteps.sortMapByKeyDescAndReturnValue(priceFromAndTitleEngMap);
        List<String> sortActualList = getSortActualList(sort, loginResponse);
        assertThat(sortActualList, is(sortExpectedList));
    }

    //Сортировку по Площади сделала, но тесты падают, из-за того, что у многих ЖК совпадает площадь От/До, а
    //логика сортировки при одинаковой площади - неизвестна (делал Печерский, спросить не у кого)

    @Test
    @Disabled
    @DisplayName("Проверить сортировку ЖК 'Площадь по возрастанию' на странице Мое избранное")
    void checkSortFavoritesAreaAsc() throws InterruptedException {
        String sort = "Площадь по возрастанию";
        UserFavoritesApiSteps.addBuildingToUserFavoritesUseAccessToken(accessToken);
        Map<String, Double> squareM2FromAndTitleEngMap = UserFavoritesApiSteps.getUserFavoritesBuildingSquareM2FromAndTitleEngMap(accessToken);
        List<String> sortExpectedList = UserFavoritesApiSteps.sortMapByValueAscAndReturnKey(squareM2FromAndTitleEngMap);
        List<String> sortActualList = getSortActualList(sort, loginResponse);
        assertThat(sortActualList, is(sortExpectedList));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Площадь по убыванию' на странице Мое избранное")
    @Disabled
    void checkSortFavoritesAreaDesc() throws InterruptedException {
        String sort = "Площадь по убыванию";
        UserFavoritesApiSteps.addBuildingToUserFavoritesUseAccessToken(accessToken);
        Map<String, Double> squareM2FromAndTitleEngMap = UserFavoritesApiSteps.getUserFavoritesBuildingSquareM2FromAndTitleEngMap(accessToken);
        List<String> sortExpectedList = UserFavoritesApiSteps.sortMapByValueDescAndReturnKey(squareM2FromAndTitleEngMap);
        List<String> sortActualList = getSortActualList(sort, loginResponse);
        assertThat(sortActualList, is(sortExpectedList));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена за м2 - по возрастанию' на странице Мое избранное")
    void checkSortFavoritesPriceM2Asc() throws InterruptedException {
        String sort = "Цена за м2 - по возрастанию";
        UserFavoritesApiSteps.addBuildingToUserFavoritesUseAccessToken(accessToken);
        Map<String, Double> priceM2FromAndTitleEngMap = UserFavoritesApiSteps.getUserFavoritesBuildingPriceM2FromAndTitleEngMap(accessToken);
        List<String> sortExpectedList = UserFavoritesApiSteps.sortMapByValueAscAndReturnKey(priceM2FromAndTitleEngMap);
        List<String> sortActualList = getSortActualList(sort, loginResponse);
        assertThat(sortActualList, is(sortExpectedList));
    }

    @Test
    @DisplayName("Проверить сортировку ЖК 'Цена за м2 - по убыванию' на странице Мое избранное")
    void checkSortFavoritesPriceM2Desc() throws InterruptedException {
        String sort = "Цена за м2 - по убыванию";
        UserFavoritesApiSteps.addBuildingToUserFavoritesUseAccessToken(accessToken);
        Map<String, Double> priceM2FromAndTitleEngMap = UserFavoritesApiSteps.getUserFavoritesBuildingPriceM2FromAndTitleEngMap(accessToken);
        List<String> sortExpectedList = UserFavoritesApiSteps.sortMapByValueDescAndReturnKey(priceM2FromAndTitleEngMap);
        List<String> sortActualList = getSortActualList(sort, loginResponse);
        assertThat(sortActualList, is(sortExpectedList));
    }
}
