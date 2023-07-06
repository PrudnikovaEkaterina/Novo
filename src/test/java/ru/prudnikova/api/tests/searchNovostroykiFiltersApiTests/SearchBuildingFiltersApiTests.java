package ru.prudnikova.api.tests.searchNovostroykiFiltersApiTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.models.buildingDto.BuildingDataDto;
import ru.prudnikova.api.steps.searchNovostroykiFiltersApiSteps.SearchBuildingFiltersApi;
@Owner("PrudnikovaEkaterina")
@Tag("Api")
public class SearchBuildingFiltersApiTests {
    @Test
    @DisplayName("Получить список ЖК с фильтром 'Станция метро' и проверить, что найденные ЖК содержат искомый station_id")
    void searchStation() {
        int stationId = 57;
        BuildingDataDto dataBuilding = SearchBuildingFiltersApi.getBuildingListWithFilterStation(stationId);
        SearchBuildingFiltersApi.checkBuildingListContainsStationId(dataBuilding, stationId);
    }

    @Test
    @DisplayName("Получить список ЖК с фильтром 'Шоссе' и проверить, что найденные ЖК содержат искомый road_id")
    void searchRoad() {
        int roadId = 68;
        BuildingDataDto dataBuilding = SearchBuildingFiltersApi.getBuildingListWithFilterRoads(roadId);
        SearchBuildingFiltersApi.checkBuildingListContainsRoadId(dataBuilding, roadId);
    }

}
