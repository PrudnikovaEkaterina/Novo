package ru.prudnikova.dataBase.entities.buildingEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceEntity {
    private String title;
    private String slug;
    private String total;
    private String per_unit;
    private int price_min;
    private int price_max;
    private int unit_price_min;
    private int unit_price_max;
    private int area_min;
    private int area_max;

}
