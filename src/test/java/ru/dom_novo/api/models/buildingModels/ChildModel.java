package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildModel {
    public int id;
    public String title_eng;
    public int parent_id;
    @JsonProperty("release_date")
    public String releaseDate;
    @JsonProperty("release_date_tag")
    public String releaseDateTag;
}
