package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NearModel {
    private ArrayList<StationModel> stations;
    private ArrayList<RoadModel> roads;
}
