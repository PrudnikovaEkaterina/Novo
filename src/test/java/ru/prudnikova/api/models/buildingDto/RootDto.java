package ru.prudnikova.api.models.buildingDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RootDto {
    private BuildingDto data;
}
