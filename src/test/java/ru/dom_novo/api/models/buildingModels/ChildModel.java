package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildModel {
    public int id;
    public String title_eng;
    public int parent_id;
    public String release_date;
    public String release_date_tag;
}
