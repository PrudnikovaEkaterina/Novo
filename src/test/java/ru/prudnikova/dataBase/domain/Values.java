package ru.prudnikova.dataBase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Values {
    @JsonProperty("18118")
    public String _18118;
    @JsonProperty("20233")
    public String _20233;
    @JsonProperty("18095")
    public String _18095;
    @JsonProperty("35099")
    public String _35099;
    @JsonProperty("18056")
    public String _18056;
    @JsonProperty("35392")
    public String _35392;
    @JsonProperty("35408")
    public String _35408;
    @JsonProperty("35808")
    public String _35808;
    @JsonProperty("35790")
    public String _35790;
    @JsonProperty("18104")
    public String _18104;
    @JsonProperty("35368")
    public String _35368;
    @JsonProperty("18224")
    public String _18224;
    @JsonProperty("18060")
    public String _18060;
    @JsonProperty("18063")
    public String _18063;
    @JsonProperty("18062")
    public String _18062;
    @JsonProperty("18061")
    public String _18061;
    @JsonProperty("18105")
    public String _18105;
    @JsonProperty("18053")
    public String _18053;
    @JsonProperty("31020")
    public String _31020;
    @JsonProperty("18109")
    public String _18109;
    @JsonProperty("35265")
    public String _35265;
    @JsonProperty("18172")
    public String _18172;
    @JsonProperty("18237")
    public String _18237;
    @JsonProperty("18103")
    public String _18103;
    @JsonProperty("35796")
    public Map<String, String> _35796;
}
