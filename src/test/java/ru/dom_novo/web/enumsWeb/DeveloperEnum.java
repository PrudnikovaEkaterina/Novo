package ru.dom_novo.web.enumsWeb;

public enum DeveloperEnum {
    GK_A101("ГК «А101»"),
    LEVEL_GROUP("Level Group"),
    GK_KOROTOS("ГК «Кортрос»");

    public final String name;

    DeveloperEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
