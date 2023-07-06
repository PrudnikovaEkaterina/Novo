package ru.dom_novo.dataBase.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.dataBase.entities.buildingEntities.*;
import ru.dom_novo.testData.GenerationData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingService {

    public static List<Integer> getBuildingIdList(List<BuildingEntity> buildingEntityList) {
        return buildingEntityList.stream().map(BuildingEntity::getId).collect(Collectors.toList());
    }

    public static int getBuildingIdWithoutFlatsWherePricesExistAreaMin() {
        List<Integer> buildingIdList = getBuildingIdList(BuildingDao.selectBuildingIdWithoutFlatsWherePricesExistAreaMin());
        assert buildingIdList != null;
        return GenerationData.setRandomBuildingId(buildingIdList);
    }

    public static int getBuildingIdWithoutFlatsWherePricesExistUnitPriceMin() {
        List<Integer> buildingIdList = getBuildingIdList(BuildingDao.selectBuildingIdWithoutFlatsWherePricesExistUnitPriceMin());
        assert buildingIdList != null;
        return GenerationData.setRandomBuildingId(buildingIdList);
    }

    public static int getBuildingIdWithoutFlatsWherePricesSlugExistRoom(String slugRoom) {
        List<Integer> buildingIdList = getBuildingIdList(BuildingDao.selectBuildingIdWithoutFlatsWherePricesSlugExistRoom(slugRoom));
        assert buildingIdList != null;
        return GenerationData.setRandomBuildingId(buildingIdList);

    }

    @Step("Преобразование поля data_json в объект DataJsonEntity")
    public static DataJsonEntity getDataJsonEntity(int buildingId) throws IOException {
        String dataJson = BuildingDao.selectBuildingDataJson(buildingId);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(dataJson, new TypeReference<>() {
        });
    }

    @Step("Получение минимальной цены для ЖК без преложений от Тренд Агента из базы")
    public static int selectPriceMin(int buildingId, String title) throws IOException {
        DataJsonEntity dataJsonEntity = getDataJsonEntity(buildingId);
        List<PriceEntity> priceEntityList = dataJsonEntity.getPrices();
        int price = 0;
        for (PriceEntity priceEntity : priceEntityList) {
            if (priceEntity.getTitle().equals(title))
                price = priceEntity.getPrice_min();
        }
        return price;
    }

    @Step("Получение минимальной цены за ква для ЖК без преложений от Тренд Агента из базы")
    public static int selectUnitPriceMin(int buildingId, String title) throws IOException {
        DataJsonEntity dataJsonEntity = getDataJsonEntity(buildingId);
        List<PriceEntity> priceEntityList = dataJsonEntity.getPrices();
        int unitPriceMin = 0;
        for (PriceEntity priceEntity : priceEntityList) {
            if (priceEntity.getTitle().equals(title))
                unitPriceMin = priceEntity.getUnit_price_min();
        }
        return unitPriceMin;
    }


    @Step("Получение минимальной площади для ЖК без преложений от Тренд Агента из базы")
    public static double selectAreaMin(int buildingId, String title) throws IOException {
        DataJsonEntity dataJsonEntity = getDataJsonEntity(buildingId);
        List<PriceEntity> priceEntityList = dataJsonEntity.getPrices();
        double areaMin = 0;
        for (PriceEntity priceEntity : priceEntityList) {
            if (priceEntity.getTitle().equals(title))
                areaMin = priceEntity.getArea_min();
        }
        return areaMin;
    }

    public static List<Integer> getBuildingIdFromBuildingsWhereParentIdIs(int parentId) {
        List<Integer> buildingIdList = getBuildingIdList(BuildingDao.selectAllFromBuildingsWhereParentIdIs(parentId));
        assert buildingIdList != null;
        return buildingIdList;
    }
//    @Step("Получить год сдачи ЖК") (устарело, для примера)
//    public static int selectBuildingReleaseYear(int buildingId) throws IOException {
//        String dataJson = BuildingDao.selectBuildingDataJson(buildingId);
//        ObjectMapper objectMapper = new ObjectMapper();
//        Object releaseYear = JsonPath.read(dataJson, "$.properties.241.values.*");
//        String releaseYearStr = objectMapper.writeValueAsString(releaseYear);
//        return RegexpMeth.extractYear(releaseYearStr);
//    }

}
