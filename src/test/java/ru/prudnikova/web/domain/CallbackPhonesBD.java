package ru.prudnikova.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallbackPhonesBD {
    private String phone;
    @JsonProperty("user_id")
    private String userId;
    private String link;
}
