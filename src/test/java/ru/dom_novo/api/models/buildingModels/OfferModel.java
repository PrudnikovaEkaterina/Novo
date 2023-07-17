package ru.dom_novo.api.models.buildingModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferModel {
    private String key;
    private ParamsModel params;
    private Integer total;
    private PriceModel price;
    @JsonProperty("price_m2")
    private PriceM2Model priceM2;
    @JsonProperty("square_m2")
    private SquareM2Model squareM2;
}
