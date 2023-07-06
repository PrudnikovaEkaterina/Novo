package ru.dom_novo.api.models.authDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginBodyDto {
    private String password;
    private String phone;
}
