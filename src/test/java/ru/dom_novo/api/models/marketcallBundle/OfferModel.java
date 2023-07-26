package ru.dom_novo.api.models.marketcallBundle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferModel {
    private Integer id;
    private String title;
    private String state;
    @JsonProperty("move_building_id")
    private String moveBuildingId;
    @JsonProperty("move_newbuilding_secondary_id")
    private String moveNewbuildingSecondaryId;
}
