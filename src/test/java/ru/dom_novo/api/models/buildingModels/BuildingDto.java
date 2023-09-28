package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingDto {
    private Integer id;
    @JsonProperty("title_eng")
    private String titleEng;
    private NearModel near;
    @JsonProperty("parent_id")
    private Integer parentId;
    private FlatModel flats;
    @JsonProperty("release_date")
    private String releaseDate;
    private ReleaseModel release;
    private List<DocumentModel> documents;
    private LocationModel location;
    private Integer apartments;
    private List<String> square;
    @JsonProperty("floor_range")
    private List<String> floorRange;
    private Map<String, ChildModel> children = new HashMap<>();
}
