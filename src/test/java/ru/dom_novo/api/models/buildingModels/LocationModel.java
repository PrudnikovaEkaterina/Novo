package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationModel {
    @JsonProperty("address_short")
    private String addressShort;
    private String district;
    @JsonProperty("gar_object_id")
    private Integer garObjectId;
    private Double lat;
    private Double lng;
}
