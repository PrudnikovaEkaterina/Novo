package ru.dom_novo.api.tests.sitemap.geo;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.models.buildingModels.RootModel;
import ru.dom_novo.api.models.buildingModels.StationModel;
import ru.dom_novo.api.models.sitemap.geo.RootSitemapGeoModel;
import ru.dom_novo.api.steps.cardNovostroykiApiSteps.CardNovostroykiApiSteps;
import ru.dom_novo.api.steps.sitemapGeoSteps.SitemapGeoSteps;
import ru.dom_novo.dataBase.dao.BuildingDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class SitemapGeoTests {
    @Test
    @Description("Проверить, что количество уникальных станций метро для ЖК с предложениями соответсвует данным роута api/sitemap/xml/geo")
    void checkStation() {
        List<Integer> buildingIdList = BuildingDao.selectDistinctBuildingIdFromFlats();
        Set<Integer> idSetForTypeStation = new HashSet<>();
        for (Integer id : buildingIdList) {
            RootModel data = CardNovostroykiApiSteps.getBuildingData(id);
            List<StationModel> stationsList = CardNovostroykiApiSteps.getStationList(data);
                if (stationsList != null) {
                    for (StationModel stationModel : stationsList) {
                        idSetForTypeStation.add(stationModel.getId());
                    }
                }
            }
        RootSitemapGeoModel data = SitemapGeoSteps.getData();
        List<Integer> idListForTypeStation = SitemapGeoSteps.getIdListForTypeStation(data);
        Assertions.assertEquals(idListForTypeStation.size(), idSetForTypeStation.size());
    }
}
