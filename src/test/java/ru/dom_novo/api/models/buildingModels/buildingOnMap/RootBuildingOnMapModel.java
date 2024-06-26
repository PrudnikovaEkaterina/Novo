package ru.dom_novo.api.models.buildingModels.buildingOnMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RootBuildingOnMapModel {
    @JsonProperty("data")
    private List<BuildingOnMapModel> buildingOnMapModelsList;
}
