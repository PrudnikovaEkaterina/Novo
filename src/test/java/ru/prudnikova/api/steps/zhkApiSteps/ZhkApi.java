package ru.prudnikova.api.steps.zhkApiSteps;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Step;
import ru.prudnikova.api.models.buildingDto.BuildingDto;
import ru.prudnikova.api.models.buildingDto.DataBuildingDto;
import ru.prudnikova.dataBase.managers.BuildingDAO;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.*;
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
    public static Map<Integer, List<Integer>> getReleaseDate(DataBuildingDto dataBuildingDto) {
        Map<Integer, String> mapa = dataBuildingDto.getData().stream().collect(toMap(BuildingDto::getId, BuildingDto::getReleaseDate));
        System.out.println(mapa);
        Map<Integer, List<Integer>> mapa1 = mapa.entrySet()
                .stream()
                .collect(toMap(Entry::getKey, el-> extractYearsToList(el.getValue())));
        System.out.println(mapa1);
        return mapa1;
    }

    public static List<Integer> extractYearsToList(String source) {
        List<Integer> result = new ArrayList();
        Pattern p = Pattern.compile("\\d{4}");
        Matcher m = p.matcher(source);
        while (m.find()) {
            result.add(Integer.parseInt(m.group()));
        }
        return result;
    }
    @Step("Получить год сдачи ЖК")
    public static void selectBuildingReleaseYear() throws IOException {
     BuildingDAO.selectBuildingReleaseYear();

    }
}
