package ru.prudnikova.api.steps.zhkApiSteps;

import io.qameta.allure.Step;
import ru.prudnikova.api.models.buildingDto.BuildingDto;
import ru.prudnikova.api.models.buildingDto.DataBuildingDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.prudnikova.api.specifications.Specification.requestSpec;
import static ru.prudnikova.api.specifications.Specification.responseSpec200;

public class ZhkApi {
    @Step("Получить список Похожих ЖК")
    public static DataBuildingDto getSimilarBuildingList(int zhkId) {
        return given()
                .spec(requestSpec)
                .basePath("/api/buildings/"+zhkId+"/similar/")
                .param("per_page", 16)
                .param("page", 1)
                .get()
                .then()
                .spec(responseSpec200)
                .extract().as(DataBuildingDto.class);
    }

    @Step("Получить список Дат сдачи из списка ЖК")
    public static Map<Integer, String> getReleaseDate(DataBuildingDto dataBuildingDto) {
        Map<Integer, String> mapa = dataBuildingDto.getData().stream().collect(Collectors.toMap(BuildingDto::getId, BuildingDto::getReleaseDate));
        System.out.println(mapa);
        return mapa;
    }
}
