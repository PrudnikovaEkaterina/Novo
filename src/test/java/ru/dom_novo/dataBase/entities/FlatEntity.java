package ru.dom_novo.dataBase.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlatEntity {
    private int id;
    @JsonProperty("building_id")
    private int buildingId;
    private int status;
    private int rooms;
    @JsonProperty("area_total")
    private int areaTotal;
    @JsonProperty("price_total")
    private int priceTotal;
    private int mortgage;
    @JsonProperty("military_mortgage")
    private int militaryMortgage;
    private int subsidy;
    private int installment;
    private int floor;
    private int floors;
    @JsonProperty("ceiling_height")
    private int ceilingHeight;
    private int finishing;
}
