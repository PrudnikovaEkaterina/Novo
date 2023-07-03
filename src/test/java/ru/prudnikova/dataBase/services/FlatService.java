package ru.prudnikova.dataBase.services;

import ru.prudnikova.dataBase.dao.FlatDao;
import ru.prudnikova.dataBase.entities.FlatsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FlatService {

    public static List<Integer> getFlatIdList(List<FlatsEntity> flatsEntityList) {
        return flatsEntityList.stream().map(FlatsEntity::getBuildingId).collect(Collectors.toList());
    }

    public static List<Integer> getBuildingIdFromFlatsWithFilterRenovation(String renovation){
        return getFlatIdList(FlatDao.selectBuildingIdFromFlatsWithFilterRenovation(renovation));
    }

    public static List<Integer> getBuildingIdFromFlatsWithFilterPaymentMethod(String paymentMethod){
        return getFlatIdList(FlatDao.selectBuildingIdFromFlatsWithFilterPaymentMethod(paymentMethod));
    }

    public static List<Integer> getBuildingIdFromFlatsWhereFloorGreaterOrEqualsFloorUnit(int floorUnit) {
        return getFlatIdList(FlatDao.selectBuildingIdFromFlatsWhereFloorGreaterOrEqualsFloorUnit(floorUnit));
    }

}
