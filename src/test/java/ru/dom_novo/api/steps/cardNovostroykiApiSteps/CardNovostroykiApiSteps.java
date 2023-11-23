package ru.dom_novo.api.steps.cardNovostroykiApiSteps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import ru.dom_novo.api.models.buildingModels.*;
import ru.dom_novo.regexp.RegexpMeth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.dom_novo.api.specifications.Specification.requestSpec;
import static ru.dom_novo.api.specifications.Specification.responseSpec200;

public class CardNovostroykiApiSteps {

    @Step("Получить информацию о ЖК")
    public static RootModel getBuildingData(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(RootModel.class);
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
    @Step("Получить минимальную цену за квадратный метр в ЖК")
    public static int getPriceM2From(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.flats.price_m2.from");
    }

    @Step("Получить список минимальных цен квартир ЖК")
    public static List<Integer> getFlatsOffersPriceFromList (int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.flats.offers.price.from");
    }
    @Step("Получить список минимальных площадей для квартир в ЖК")
    public static List<String> getFlatsOffersSquareM2FromList (int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.flats.offers.square_m2.from");
    }

    @Step("Получить год сдачи ЖК")
    public static int getReleaseYear(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().path("data.release.year");
    }

    @Step("Получить дату сдачи ЖК")
    public static String getReleaseDate(int buildingId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/" + buildingId)
                .get()
                .then()
                .statusCode(200)
                .extract().path("data.release_date");
    }

    @Step("Получить список station_id")
    public static List<Integer> getStationIdList(int buildingId) {
        RootModel data = getBuildingData(buildingId);
        List<Integer> list = new ArrayList<>();
        if (data.getData().getNear().getStations() != null) {
            list = data.getData().getNear().getStations().stream().map(StationModel::getId).collect(Collectors.toList());
        }
        return list;
    }

    @Step("Получить значение district_id")
    public static int getDistrictId(int buildingId) {
        RootModel data = getBuildingData(buildingId);
        int district = 0;
        if (data.getData().getLocation().getDistrict() != null) {
            district = Integer.parseInt(data.getData().getLocation().getDistrict());
        }
        return district;
    }

    @Step("Получить значение сity_id")
    public static int getCityId(int buildingId) {
        RootModel data = getBuildingData(buildingId);
        int cityId = 0;
        if (data.getData().getLocation().getGeo().getCities() != null) {
            cityId = data.getData().getLocation().getGeo().getCities().getId();
        }
        return cityId;
    }

    @Step("Получить список road_id")
    public static List<Integer> getRoadIdList(int buildingId) {
        RootModel data = getBuildingData(buildingId);
        List<Integer> list = new ArrayList<>();
        if (data.getData().getNear().getRoads() != null) {
            list = data.getData().getNear().getRoads().stream().map(RoadModel::getId).collect(Collectors.toList());
        }
        return list;
    }

    @Step("Получить список floor_range")
    public static List<Integer> floorRangeList(int buildingId) {
        try {
            return CardNovostroykiApiSteps.getBuildingData(buildingId).getData().getFloorRange().stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Step("Получить список floor_range для каждого корпуса ЖК")
    public static List<List<Integer>> getTotalFloorHouseList(List<Integer> houseIdList) {
        List<List<Integer>> totalFloorHouseList = new ArrayList<>();
        for (Integer houseId : houseIdList) {
            List<Integer> floorRangeHouseList = CardNovostroykiApiSteps.floorRangeList(houseId);
            if (floorRangeHouseList != null) {
                List<Integer> floorHouseList = new ArrayList<>();
                switch (floorRangeHouseList.size()) {
                    case 1:
                        floorHouseList = List.of(1, floorRangeHouseList.get(0));
                        break;
                    case 2:
                        floorHouseList = List.of(1, floorRangeHouseList.get(1));
                        break;
                }
                totalFloorHouseList.add(floorHouseList);
            }
        }
        return totalFloorHouseList;
    }

    @Step("Получить список ")
    public static List<List<Long>> getOffersPriceList(RootModel root, int priceMax) {
        List<PriceModel> priceModelList = root.getData().getFlats().getOffers().stream().map(OfferModel::getPrice).collect(Collectors.toList());
        List<List<Long>> totalList = new ArrayList<>();
        for (PriceModel el : priceModelList) {
            List<Long> priceList = new ArrayList<>();
            if (el.getFrom() == null && el.getTo() != null) {
                priceList.add(0L);
                priceList.add(el.getTo());
            } else if (el.getFrom() != null && el.getTo() == null) {
                priceList.add(el.getFrom());
                priceList.add((long) priceMax);
            } else {
                priceList.add(el.getFrom());
                priceList.add(el.getTo());
            }
            totalList.add(priceList);
        }
        return totalList;
    }

    @Step("Получить список")
    public static List<Long> getFlatsPriceList(RootModel root, int priceMax) {
        List<Long> priceList = new ArrayList<>();
        Long priceFrom = null;
        Long priceTo = null;
        try {
            priceFrom = root.getData().getFlats().getPrice().getFrom();
            priceTo = root.getData().getFlats().getPrice().getTo();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (priceFrom == null && priceTo != null) {
            priceList.add(0L);
            priceList.add(priceTo);
        } else if (priceFrom != null && priceTo == null) {
            priceList.add(priceFrom);
            priceList.add((long) priceMax);
        } else {
            priceList.add(priceFrom);
            priceList.add(priceTo);
        }
        return priceList;
    }

    @Step("Проверить, что хотя бы один из корпусов соответсвует фильтру по сроку сдачи")
    public static void checkHousesReleaseDate(List<String> listReleaseDate, String releaseValue, int releaseYear, int releaseQuarter) {
        if (!listReleaseDate.isEmpty()) {
            if (listReleaseDate.stream().noneMatch(el -> el.contains(releaseValue))) {
                List<List<Integer>> collect = listReleaseDate.stream().map(RegexpMeth::getListNumbersFromString).collect(Collectors.toList());
                if (collect.stream().noneMatch(el -> el.get(1) < releaseYear)) {
                    Assertions.assertTrue(collect.stream().filter(el -> el.get(1) == releaseYear).anyMatch(el -> el.get(0) <= releaseQuarter));
                }
            }
        }
    }
}
