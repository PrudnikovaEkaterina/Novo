package ru.prudnikova.api.tests.zhkApiTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.models.buildingDto.DataBuildingDto;
import ru.prudnikova.api.steps.zhkApiSteps.ZhkApi;
import ru.prudnikova.dataBase.managers.BuildingDAO;

import java.io.IOException;

@Owner("PrudnikovaEkaterina")
@Tag("Api")
public class ZhkApiTests {

    @Test
    @DisplayName("Получить и проверить список Похожих ЖК")
    void checkSimilarBuildingList() throws IOException {
        int zhkId = 15054;
        DataBuildingDto dataBuilding = ZhkApi.getSimilarBuildingList(zhkId);
        ZhkApi.getReleaseDate(dataBuilding);
        ZhkApi.selectBuildingReleaseYear();

    }
}
