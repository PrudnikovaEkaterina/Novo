package ru.dom_novo.testData;

import com.github.javafaker.Faker;
import ru.dom_novo.web.enumsWeb.BuildingEnum;
import ru.dom_novo.web.enumsWeb.CityEnum;
import ru.dom_novo.web.enumsWeb.DeveloperEnum;
import ru.dom_novo.web.enumsWeb.HousingClassEnum;

import java.util.List;


public class GenerationData {
    static Faker faker = new Faker();

    public static String setRandomDeveloper() {
        return faker.options().option(DeveloperEnum.values()).name;
    }

    public static String setRandomBuilding() {
        return faker.options().option(BuildingEnum.values()).name;
    }
    public static int setRandomBuildingId() {
        return faker.options().option(BuildingEnum.values()).id;
    }

    public static String setRandomCity() {
        return faker.options().option(CityEnum.values()).name;
    }

    public static String setRandomHousingClass() {
        return faker.options().option(HousingClassEnum.values()).name;
    }

    public static String setRandomPhoneNumber() {
        return "7" + faker.phoneNumber().subscriberNumber(10);
    }

    public static String setRandomUserName() {
        return faker.name().firstName();
    }

    public static String setRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static int setRandomBuildingId(List<Integer> buildingIdList) {
        int id=0;
        if (buildingIdList.size()!=0){
            id = buildingIdList.get(faker.random().nextInt(buildingIdList.size()));
        }
        else
            System.out.println("List not contain id");
        return id;
    }

}
