package ru.prudnikova.dataBase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingEnity {
    private int id;
    private String parent_id;
    private String title_eng;
    private int gar_object_id;
    private int developer_id;
    private int rating;
    @JdbcTypeCode(SqlTypes.JSON)
    private String data_json;
}
