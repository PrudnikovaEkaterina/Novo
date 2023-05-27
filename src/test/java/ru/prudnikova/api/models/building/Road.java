package ru.prudnikova.api.models.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Road {
    private Integer id;
    private Integer distance_mode;
    private Integer distance;
    private String duration;
    private Integer duration_value;
}
