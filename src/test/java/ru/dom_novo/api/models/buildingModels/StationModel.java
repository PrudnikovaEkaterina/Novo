package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationModel {
    private Integer id;
    @JsonProperty("distance_mode")
    private Integer distanceMode;
    private Integer distance;
    private String duration;
    @JsonProperty("duration_value")
    private Integer durationValue;
}
