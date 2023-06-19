package ru.prudnikova.api.enumsApi;

public enum RenovationEnum {
    WITH_OUT("Без отделки", "1"),
    CLEAR("Чистовая", "2"),
    BEFORE_CLEAR("Предчистовая", "3");

    public final String name;
    public final String id;

    RenovationEnum(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

}
