package ru.prudnikova.api.models.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoadDto {
    private Integer id;
    @JsonProperty("distance_mode")
    private Integer distanceMode;
    private Integer distance;
    private String duration;
    @JsonProperty("duration_value")
    private Integer durationValue;
}
