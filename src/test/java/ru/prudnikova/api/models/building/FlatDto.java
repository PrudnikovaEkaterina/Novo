package ru.prudnikova.api.models.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlatDto {
    private Integer total;
    private PriceDto price;
    @JsonProperty("price_m2")
    private PriceM2Dto priceM2;
    @JsonProperty("square_m2")
    private SquareM2Dto squareM2;
    private ArrayList<OfferDto> offers;
}
