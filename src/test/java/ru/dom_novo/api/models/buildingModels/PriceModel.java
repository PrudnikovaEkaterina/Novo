package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceModel {
    private Long from;
    private Long to;
}
