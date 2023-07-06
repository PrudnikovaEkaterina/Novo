package ru.dom_novo.api.enumsApi;

public enum BuildingEnum {
    ZHK_WOODS(17440),
    ZHK_LUCHI(9232),
    ZHK_AVIATIKA(14962);

    public final int id;

    BuildingEnum(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }


}
