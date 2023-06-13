package ru.prudnikova.api.models.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SquareM2Dto {
    private Double from;
    public Double to;
}
