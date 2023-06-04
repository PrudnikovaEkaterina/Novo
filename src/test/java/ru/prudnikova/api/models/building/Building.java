package ru.prudnikova.api.models.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Building {
    private Integer id;
    private Near near;
    private Integer parent_id;
    private Flat flats;
}
