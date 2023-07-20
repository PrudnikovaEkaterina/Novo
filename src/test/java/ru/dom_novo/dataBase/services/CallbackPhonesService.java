package ru.dom_novo.dataBase.services;

import ru.dom_novo.dataBase.dao.CallbackPhonesDao;
import ru.dom_novo.dataBase.entities.CallbackPhonesEntity;

import java.util.List;

public class CallbackPhonesService {
    public static String getPhoneNumberFromLastCallback() {
        List<CallbackPhonesEntity> list = CallbackPhonesDao.selectLastEntryFromCallbackPhonesTables();
        return list.get(0).getPhone();
    }
    public static String getLinkFromLastCallback() {
        List<CallbackPhonesEntity> list = CallbackPhonesDao.selectLastEntryFromCallbackPhonesTables();
        return list.get(0).getLink();
    }
}
