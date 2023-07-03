package ru.prudnikova.api.steps.cardNovostroykiApiSteps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Step;
import regexp.RegexpMeth;
import ru.prudnikova.api.models.buildingDto.*;
import ru.prudnikova.dataBase.entities.buildingEntities.DataJsonEntity;
import ru.prudnikova.dataBase.entities.buildingEntities.PriceEntity;
import ru.prudnikova.dataBase.dao.BuildingDao;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.prudnikova.api.specifications.Specification.requestSpec;
import static ru.prudnikova.api.specifications.Specification.responseSpec200;

public class CardNovostroykiApi {
    @Step("Получить список Похожих ЖК")
    public static DataBuildingDto getSimilarBuildingList(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId + "/similar/")
                .param("per_page", 16)
                .param("page", 1)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(DataBuildingDto.class);
    }

//    @Step("Получить список годов сдачи для из списка Похожих ЖК из release_date")
//    public static Map<Integer, List<Integer>> getReleasesDatesLegacy(DataBuildingDto dataBuildingDto) {
//        Map<Integer, String> mapa = dataBuildingDto.getData().stream().collect(toMap(BuildingDto::getId, BuildingDto::getReleaseDate));
//        return mapa.entrySet()
//                .stream()
//                .collect(toMap(Entry::getKey, el -> RegexpMeth.extractYears(el.getValue())));
//    }

    @Step("Получить список годов сдачи для из списка Похожих ЖК из release")
    public static Map<Integer, Integer> getReleasesDates(DataBuildingDto dataBuildingDto) {
        Map<Integer, ReleaseDto> mapa = dataBuildingDto.getData().stream().collect(toMap(BuildingDto::getId, BuildingDto::getRelease));
        return mapa.entrySet()
                .stream()
                .collect(toMap(Entry::getKey, el->el.getValue().getYear()));

    }


    @Step("Получить год сдачи ЖК")
    public static int selectBuildingReleaseYear(int buildingId) throws IOException {
        String dataJson = BuildingDao.selectBuildingDataJson(buildingId);
        ObjectMapper objectMapper = new ObjectMapper();
        Object releaseYear = JsonPath.read(dataJson, "$.properties.241.values.*");
        String releaseYearStr = objectMapper.writeValueAsString(releaseYear);
        return RegexpMeth.extractYear(releaseYearStr);
    }

//    @Step("Проверить, что разница в годах сдачи - не более 2 лет")
//    public static void checkDifferenceBuildingReleaseYearLessOrEqual2YearsLegacy(int releaseYear, Map<Integer, List<Integer>> releasesDates) {
//        List<Integer> getDateRange = List.of(releaseYear - 2, releaseYear - 1, releaseYear, releaseYear + 1, releaseYear + 2);
//        List<List<Integer>> lists = new ArrayList<>(releasesDates.values());
//        for (int i = 0; i < lists.size(); i++) {
//            assert lists.get(i).stream().anyMatch(getDateRange::contains);
//        }
//    }

    @Step("Проверить, что разница в годах сдачи - не более 2 лет")
    public static void checkDifferenceBuildingReleaseYearLessOrEqual2Years(int releaseYear, Map<Integer, Integer> releases) {
        List<Integer> getDateRange = List.of(releaseYear - 2, releaseYear - 1, releaseYear, releaseYear + 1, releaseYear + 2);
        List<Integer> list = new ArrayList<>(releases.values());
        for (Integer integer : list) {
            assert getDateRange.contains(integer);
        }
    }

    @Step("Получить минимальную цену ЖК")
    public static int getPriceFrom(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.flats.price.from");
    }

    @Step("Получить список минимальных цен из списка Похожих ЖК")
    public static List<Integer> getPricesFromSimilarBuildings(DataBuildingDto dataBuildingDto) {
        return dataBuildingDto.getData().stream().map(BuildingDto::getFlats).map(FlatDto::getPrice).map(PriceDto::getFrom).collect(toList());
    }

    @Step("Посчитать разницу в процентах между минимальной ценой ЖК и минимальными ценами похожих ЖК")
    public static List<Long> calculatePercentageDifferenceBetweenPrices(int priceFrom, List<Integer> pricesFrom) {
        List<Long> listPercentageDifferenceBetweenPrices = new ArrayList<>();
        for (Integer integer : pricesFrom) {
            if (priceFrom < integer) {
                listPercentageDifferenceBetweenPrices.add(Math.round((((double) (integer - priceFrom) / priceFrom) * 100)));
            } else
                listPercentageDifferenceBetweenPrices.add(Math.round((((double) (priceFrom - integer) / priceFrom) * 100)));

        }
        return listPercentageDifferenceBetweenPrices;
    }

    @Step("Проверить, что разница в процентах между минимальной ценой ЖК и минимальными ценами похожих ЖК составляет не более 30%")
    public static void checkPercentageDifferenceLessOrEqual30 (int percent, List<Long> calculatePercentageDifferenceBetweenPrices) {
        for (int i = 0; i < calculatePercentageDifferenceBetweenPrices.size(); i++) {
            assert calculatePercentageDifferenceBetweenPrices.get(i)<=percent;
        }
    }

}
