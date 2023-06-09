package ru.dom_novo.api.models.buildingDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NearDto {
    private ArrayList<StationDto> stations;
    private ArrayList<RoadDto> roads;
}
