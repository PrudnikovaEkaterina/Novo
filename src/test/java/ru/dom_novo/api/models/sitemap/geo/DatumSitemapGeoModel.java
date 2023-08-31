package ru.dom_novo.api.models.sitemap.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatumSitemapGeoModel {
    private String type;
    private int id;
    private String title;
    @JsonProperty("title_eng")
    private String titleEng;
}
