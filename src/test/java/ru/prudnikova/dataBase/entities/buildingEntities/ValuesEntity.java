package ru.prudnikova.dataBase.entities.buildingEntities;

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
public class ValuesEntity {
    @JsonProperty("35796")
    public String year2006;
    @JsonProperty("18059")
    public String businessClass;

}
