package ru.prudnikova.api.models.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer {
    private String key;
    private Params params;
    private Integer total;
    private Price price;
    private PriceM2 price_m2;
    private SquareM2 square_m2;
}
