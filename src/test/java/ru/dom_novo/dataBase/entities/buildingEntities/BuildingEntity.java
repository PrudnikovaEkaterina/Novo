package ru.dom_novo.dataBase.entities.buildingEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingEntity {
    private Integer id;
    private String parent_id;
    private String title_eng;
    private Integer gar_object_id;
    private Integer developer_id;
    private Integer rating;
    @JdbcTypeCode(SqlTypes.JSON)
    private String data_json;
}
