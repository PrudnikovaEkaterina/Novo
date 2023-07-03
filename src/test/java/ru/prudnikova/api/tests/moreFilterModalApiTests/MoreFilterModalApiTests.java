package ru.prudnikova.api.tests.moreFilterModalApiTests;

import io.qameta.allure.Owner;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.prudnikova.api.enumsApi.RenovationEnum;
import ru.prudnikova.api.steps.moreFilterModalApiSteps.MoreFilterModalApi;
import ru.prudnikova.dataBase.dao.FlatDao;
import ru.prudnikova.dataBase.services.FlatService;

import java.util.List;

@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class MoreFilterModalApiTests {

    @Test
    @DisplayName("Применить фильтр Площадь до. Проверить, что в найденных ЖК square_m2_from меньше заданного фильтра")
    void searchSquareMax() {
        double squareMax = 15;
        MoreFilterModalApi.getBuildingListWithFilterSquareMax(squareMax);
    }

    @Test
    @DisplayName("Применить фильтр Площадь от. Проверить, что в найденных ЖК square_m2_to больше заданного фильтра")
    void searchSquareMin() {
        double squareMin = 400;
        MoreFilterModalApi.getBuildingListWithFilterSquareMin(squareMin);
    }

    @Test
    @DisplayName("Применить фильтр Этаж c. Проверить, что в найденных ЖК есть квартиры, где этаж равен или больше фильтра")
    void searchFloorMin() {
        int floorMin = 50;
        MoreFilterModalApi.checkEqualityTwoLists(MoreFilterModalApi.getBuildingListWithFilterFloorMin(floorMin),
                MoreFilterModalApi.selectBuildingListWithFilterFloorMin(floorMin));
    }

    @Test
    @DisplayName("Применить фильтр Только апартаменты и проверить, что у всех найденных объектов apartments = 1")
    void searchOnlyApartments() {
        MoreFilterModalApi.checkBuildingListWithFilterOnlyApartments();
    }

    @Test
    @DisplayName("Применить фильтр Без апартаментов и проверить, что у всех найденных объектов apartments = 0")
    void searchWithoutApartments() {
        MoreFilterModalApi.checkBuildingListWithFilterWithoutApartments();
    }

    @CsvSource(value = {"1, mortgage",
            "2, military_mortgage",
            "3, subsidy",
            "4, installment"})
    @ParameterizedTest(name = "Применить фильтр Cпособ оплаты {1}. Проверить, что в найденных ЖК есть квартиры, где {1} = 1")
    void searchMortgage(String data1, String data2) {
        List<Integer> apiList = MoreFilterModalApi.getBuildingListWithFilterMortgage(data1);
        List<Integer> dataList = MoreFilterModalApi.selectBuildingListWithFilterMortgage(data2);
        MoreFilterModalApi.checkEqualityTwoLists(apiList, dataList);

    }

    @EnumSource(RenovationEnum.class)
    @ParameterizedTest(name = "Применить фильтр Отделка = {0}. Проверить, что в найденных ЖК есть квартиры c соответствующим типом отделки")
    @Comment("Тест падает для ЖК 10375, комментарий в задаче https://tracker.yandex.ru/NOVODEV-594#649997a4a7cc4e7f81697263")
    void searchRenovation(RenovationEnum renovationEnum) {
        String renovationId = renovationEnum.id;
        List<Integer> apiList = MoreFilterModalApi.getBuildingListWithFilterRenovation(renovationId);
        System.out.println(apiList.size());
        List<Integer> dataList = FlatService.getBuildingIdFromFlatsWithFilterRenovation(renovationId);
        System.out.println(dataList.size());
        MoreFilterModalApi.checkEqualityTwoLists(apiList, dataList);

    }
}
