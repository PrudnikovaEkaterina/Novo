package ru.prudnikova.api.tests.more_filter_modal_tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.steps.more_filter_modal_steps.MoreFilterModalSteps;

@Tag("Api")
public class MoreFilterModalTests {

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("В /api/buildings/ применить фильтр Площадь до и проверить, что в найденных ЖК square_m2_from меньше заданного фильтра")
    void searchSquareMax() {
        float squareMax = 15;
        MoreFilterModalSteps.getBuildingListWithFilterSquareMax(squareMax);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("В /api/buildings/ применить фильтр Площадь от и проверить, что в найденных ЖК square_m2_to больше заданного фильтра")
    void searchSquareMin() {
        double squareMin = 400;
        MoreFilterModalSteps.getBuildingListWithFilterSquareMin(squareMin);
    }

}
