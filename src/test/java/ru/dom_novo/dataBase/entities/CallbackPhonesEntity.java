package ru.dom_novo.dataBase.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallbackPhonesEntity {
    private String phone;
    @JsonProperty("user_id")
    private String userId;
    private String link;
}
