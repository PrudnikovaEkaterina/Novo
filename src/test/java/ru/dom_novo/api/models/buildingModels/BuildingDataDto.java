package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingDataDto {
    public List<BuildingDto> data;
}
