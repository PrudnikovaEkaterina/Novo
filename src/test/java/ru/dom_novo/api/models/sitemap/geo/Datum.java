package ru.dom_novo.api.models.sitemap.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Datum {
    public String type;
    public int id;
    public String title;
    @JsonProperty("title_eng")
    public String titleEng;
}
