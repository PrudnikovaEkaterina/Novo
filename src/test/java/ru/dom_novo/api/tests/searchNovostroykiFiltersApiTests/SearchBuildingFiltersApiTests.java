package ru.dom_novo.api.tests.searchNovostroykiFiltersApiTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.models.buildingModels.BuildingDataDto;
import ru.dom_novo.api.steps.searchNovostroykiFiltersApiSteps.SearchBuildingsFiltersApiSteps;
@Owner("PrudnikovaEkaterina")
@Tag("Api")
@Disabled
public class SearchBuildingFiltersApiTests {
    @Test
    @DisplayName("Получить список ЖК с фильтром 'Станция метро' и проверить, что найденные ЖК содержат искомый station_id")
    void searchStation() {
        int stationId = 57;
        BuildingDataDto dataBuilding = SearchBuildingsFiltersApiSteps.getBuildingListWithFilterStation(stationId);
        SearchBuildingsFiltersApiSteps.checkBuildingListContainsStationId(dataBuilding, stationId);
    }

    @Test
    @DisplayName("Получить список ЖК с фильтром 'Шоссе' и проверить, что найденные ЖК содержат искомый road_id")
    void searchRoad() {
        int roadId = 68;
        BuildingDataDto dataBuilding = SearchBuildingsFiltersApiSteps.getBuildingListWithFilterRoads(roadId);
        SearchBuildingsFiltersApiSteps.checkBuildingListContainsRoadId(dataBuilding, roadId);
    }

}
