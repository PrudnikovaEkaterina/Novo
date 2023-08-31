package ru.dom_novo.api.models.sitemap.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RootSitemapGeoModel {
    public List<DatumSitemapGeoModel> data;
}
