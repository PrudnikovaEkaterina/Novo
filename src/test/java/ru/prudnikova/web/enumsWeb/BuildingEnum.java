package ru.prudnikova.web.enumsWeb;

public enum BuildingEnum {
    GORODSKIE_ISTORII("ЖК «Городские истории»", 7874),
    LUCHI("ЖК «Лучи»", 9232),
    ILOVE("ЖК ILOVE", 4208);

    public final String name;
    public final int id;

    BuildingEnum(String name, int id) {
        this.name = name;
        this.id=id;
    }

    @Override
    public String toString() {
        return name+" "+id;
    }
}
