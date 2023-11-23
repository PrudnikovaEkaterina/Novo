package ru.dom_novo.web.enumsWeb;

public enum CityEnum {
    LYUBERTSY("Люберцы"),
    HIMKI("Химки"),
    MITISHI("Мытищи");

    public final String name;

    CityEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
