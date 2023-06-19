package ru.prudnikova.web.enumsWeb;

public enum CityEnum {
    LYUBERTSY("Люберцы"),
    HIMKI("Химки"),
    DMITROV("Дмитров");

    public final String name;

    CityEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
