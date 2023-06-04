package ru.prudnikova.api.models.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flat {
    private Integer total;
    private Price price;
    private PriceM2 price_m2;
    private SquareM2 square_m2;
    private ArrayList<Offer> offers;
}
