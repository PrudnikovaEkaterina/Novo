package ru.prudnikova.web.enums;

public enum BuildingEnum {
    GORODSKIE_ISTORII("ЖК «Городские истории»"), LUCHI("ЖК «Лучи»"), ILOVE ("ЖК ILOVE");

    public final String name;
    BuildingEnum(String name) {
        this.name =name;
    }

    @Override
    public String toString() {
        return name;
    }
}
