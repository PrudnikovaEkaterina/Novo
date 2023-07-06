package ru.dom_novo.api.models.buildingDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseDto {
    private Integer year;
}
