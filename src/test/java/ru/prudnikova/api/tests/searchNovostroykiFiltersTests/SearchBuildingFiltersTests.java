package ru.prudnikova.api.tests.searchNovostroykiFiltersTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.models.building.DataBuildingDto;
import ru.prudnikova.api.steps.searchNovostroykiFiltersSteps.SearchBuildingFiltersApi;

public class SearchBuildingFiltersTests {

    @Test
    @Owner("PrudnikovaEkaterina")
    @Tag("Api")
    @DisplayName("В /api/buildings/ Применить фильтр 'Станция метро' и проверить, что найденные ЖК содержат искомый station_id")
    void searchStation() {
        int stationId = 57;
        DataBuildingDto dataBuilding = SearchBuildingFiltersApi.getBuildingListWithFilterStation(stationId);
        SearchBuildingFiltersApi.checkBuildingListContainsStationId(dataBuilding, stationId);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @Tag("Api")
    @DisplayName("В /api/buildings/ применить фильтр 'Шоссе' и проверить, что найденные ЖК содержат искомый road_id")
    void searchRoad() {
        int roadId = 68;
        DataBuildingDto dataBuilding = SearchBuildingFiltersApi.getBuildingListWithFilterRoads(roadId);
        SearchBuildingFiltersApi.checkBuildingListContainsRoadId(dataBuilding, roadId);
    }

}
