package ru.dom_novo.web.enumsWeb;

public enum DeveloperEnum {
    GK_А101("ГК «А101»"),
    GK_MIC("ГК «МИЦ»"),
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
