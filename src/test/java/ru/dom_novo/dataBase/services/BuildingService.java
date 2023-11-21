package ru.dom_novo.dataBase.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.qameta.allure.Step;
import ru.dom_novo.dataBase.dao.BuildingDao;
import ru.dom_novo.dataBase.entities.buildingEntities.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingService {

    public static List<Integer> getBuildingIdList(List<BuildingEntity> buildingEntityList) {
        return buildingEntityList.stream().map(BuildingEntity::getId).collect(Collectors.toList());
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


    public static List<Integer> getBuildingIdFromBuildingsWhereParentIdIs(int parentId) {
        List<Integer> buildingIdList = getBuildingIdList(BuildingDao.selectAllFromBuildingsWhereParentIdIs(parentId));
        assert buildingIdList != null;
        return buildingIdList;
    }
    public static String selectProperties202Values(int buildingId) throws JsonProcessingException {
        String dataJson = BuildingDao.selectBuildingDataJson(buildingId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Object roomType = JsonPath.read(dataJson, "$.properties.202.values.*");
            return objectMapper.writeValueAsString(roomType);
        } catch (PathNotFoundException e) {
            return null;
        }
    }

        public static String selectPricesTitle(int buildingId) throws JsonProcessingException {
            String dataJson = BuildingDao.selectBuildingDataJson(buildingId);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Object roomType = JsonPath.read(dataJson, "$.prices[*].title");
                return objectMapper.writeValueAsString(roomType);
            }
            catch (PathNotFoundException e){
                return null;
            }
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
