package ru.prudnikova.api.tests.searchNovostroykiFiltersTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.models.building.DataBuilding;
import ru.prudnikova.api.steps.searchNovostroykiFiltersSteps.SearchBuildingFiltersSteps;

public class SearchBuildingFiltersTests {

    @Test
    @Owner("PrudnikovaEkaterina")
    @Tag("Api")
    @DisplayName("В /api/buildings/ Применить фильтр 'Станция метро' и проверить, что найденные ЖК содержат искомый station_id")
    void searchStation() {
        int stationId = 57;
        DataBuilding dataBuilding = SearchBuildingFiltersSteps.getBuildingListWithFilterStation(stationId);
        SearchBuildingFiltersSteps.checkBuildingListContainsStationId(dataBuilding, stationId);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @Tag("Api")
    @DisplayName("В /api/buildings/ применить фильтр 'Шоссе' и проверить, что найденные ЖК содержат искомый road_id")
    void searchRoad() {
        int roadId = 68;
        DataBuilding dataBuilding = SearchBuildingFiltersSteps.getBuildingListWithFilterRoads(roadId);
        SearchBuildingFiltersSteps.checkBuildingListContainsRoadId(dataBuilding, roadId);
    }

}
