package ru.dom_novo.dataBase.services;

import ru.dom_novo.dataBase.dao.FlatDao;
import ru.dom_novo.dataBase.entities.FlatEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FlatService {

    public static List<Integer> getFlatIdList(List<FlatEntity> flatsEntityList) {
        return flatsEntityList.stream().map(FlatEntity::getBuildingId).collect(Collectors.toList());
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
