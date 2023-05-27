package ru.prudnikova.api.models.building;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Near {
    private ArrayList<Station> stations;
    private ArrayList<Road> roads;
}
