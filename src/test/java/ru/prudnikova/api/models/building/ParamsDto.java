package ru.prudnikova.api.models.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamsDto {
    private String rooms;
    @JsonProperty("nb_id")
    private Integer nbId;
}
