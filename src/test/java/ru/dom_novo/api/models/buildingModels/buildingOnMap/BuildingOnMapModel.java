package ru.dom_novo.api.models.buildingModels.buildingOnMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingOnMapModel {
    private Integer id;
    private Double lat;
    private Double lng;
    private Integer total;
    @JsonProperty("min_price")
    private Integer minPrice;
}
