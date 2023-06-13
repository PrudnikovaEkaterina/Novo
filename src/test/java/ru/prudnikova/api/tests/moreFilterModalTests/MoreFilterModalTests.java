package ru.prudnikova.api.tests.moreFilterModalTests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.prudnikova.api.steps.moreFilterModalSteps.MoreFilterModalApi;

import java.util.List;

@Tag("Api")
public class MoreFilterModalTests {

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Применить фильтр Площадь до. Проверить, что в найденных ЖК square_m2_from меньше заданного фильтра")
    void searchSquareMax() {
        double squareMax = 15;
        MoreFilterModalApi.getBuildingListWithFilterSquareMax(squareMax);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Применить фильтр Площадь от. Проверить, что в найденных ЖК square_m2_to больше заданного фильтра")
    void searchSquareMin() {
        double squareMin = 400;
        MoreFilterModalApi.getBuildingListWithFilterSquareMin(squareMin);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Применить фильтр Этаж c. Проверить, что в найденных ЖК есть квартиры, где этаж равен или больше фильтра")
    void searchFloorMin() {
        int floorMin = 50;
        MoreFilterModalApi.checkEqualityTwoLists(MoreFilterModalApi.getBuildingListWithFilterFloorMin(floorMin),
                MoreFilterModalApi.selectBuildingListWithFilterFloorMin(floorMin));
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Применить фильтр Только апартаменты и проверить, что у всех найденных объектов apartments = 1")
    void searchOnlyApartments() {
        MoreFilterModalApi.checkBuildingListWithFilterOnlyApartments();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Применить фильтр Без апартаментов и проверить, что у всех найденных объектов apartments = 0")
    void searchWithoutApartments() {
        MoreFilterModalApi.checkBuildingListWithFilterWithoutApartments();
    }

    @CsvSource(value = {"1, mortgage",
                        "2, military_mortgage",
                        "3, subsidy",
                        "4, installment"})
    @ParameterizedTest(name ="Применить фильтр Cпособ оплаты {1}. Проверить, что в найденных ЖК есть квартиры, где {1} = 1" )
    @Owner("PrudnikovaEkaterina")
    void searchMortgage(String data1, String data2) {
        List<Integer> apiList = MoreFilterModalApi.getBuildingListWithFilterMortgage(data1);
        List<Integer> dataList = MoreFilterModalApi.selectBuildingListWithFilterMortgage(data2);
        MoreFilterModalApi.checkEqualityTwoLists(apiList,dataList);

    }
}
