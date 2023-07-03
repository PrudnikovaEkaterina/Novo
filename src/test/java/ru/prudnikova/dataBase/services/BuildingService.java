package ru.prudnikova.dataBase.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import ru.prudnikova.api.steps.cardNovostroykiApiSteps.CardNovostroykiApi;
import ru.prudnikova.dataBase.dao.BuildingDao;
import ru.prudnikova.dataBase.entities.buildingEntities.BuildingEntity;
import ru.prudnikova.dataBase.entities.buildingEntities.DataJsonEntity;
import ru.prudnikova.dataBase.entities.buildingEntities.PriceEntity;
import ru.prudnikova.testData.GenerationData;

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

}
