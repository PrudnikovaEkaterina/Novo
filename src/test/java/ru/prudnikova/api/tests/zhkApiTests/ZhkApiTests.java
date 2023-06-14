package ru.prudnikova.api.tests.zhkApiTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.models.buildingDto.DataBuildingDto;
import ru.prudnikova.api.steps.zhkApiSteps.ZhkApi;

@Owner("PrudnikovaEkaterina")
@Tag("Api")
public class ZhkApiTests {

    @Test
    @DisplayName("Получить и проверить список Похожих ЖК")
    void checkSimilarBuildingList() {
        int zhkId = 15054;
        DataBuildingDto dataBuilding = ZhkApi.getSimilarBuildingList(zhkId);
        ZhkApi.getReleaseDate(dataBuilding);
    }
}
