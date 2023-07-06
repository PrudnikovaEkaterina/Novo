package ru.dom_novo.web.enumsWeb;

public enum RoomEnum {
    STUDIO("Студ"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4");

    public final String name;

    RoomEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
