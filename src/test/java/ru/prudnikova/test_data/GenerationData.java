package ru.prudnikova.test_data;

import com.github.javafaker.Faker;
import ru.prudnikova.web.enums.BuildingEnum;
import ru.prudnikova.web.enums.CityEnum;
import ru.prudnikova.web.enums.DeveloperEnum;
import ru.prudnikova.web.enums.HousingClassEnum;


public class GenerationData {
    static Faker faker = new Faker();

    public static String setRandomDeveloper(DeveloperEnum[] allEnumValues) {
        return faker.options().option(DeveloperEnum.values()).name;
    }

    public static String setRandomBuilding(BuildingEnum[] allEnumValues) {
        return faker.options().option(BuildingEnum.values()).name;
    }

    public static String setRandomCity(CityEnum[] allEnumValues) {
        return faker.options().option(CityEnum.values()).name;
    }

    public static String setRandomHousingClass(HousingClassEnum[] allEnumValues) {
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

}
