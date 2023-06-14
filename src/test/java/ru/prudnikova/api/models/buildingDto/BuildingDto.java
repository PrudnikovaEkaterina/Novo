package ru.prudnikova.api.models.buildingDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingDto {
    private Integer id;
    private NearDto near;
    @JsonProperty("parent_id")
    private Integer parentId;
    private FlatDto flats;
    @JsonProperty("release_date")
    private String releaseDate;
}
