package ru.dom_novo.dataBase.entities.buildingEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertiesEntity {
    @JsonProperty("241")
    private ReleaseYearEntity releaseYear;
    @JsonProperty("194")
    private HousingClassEntity housingClass;
}
