package ru.prudnikova.api.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginBodyDto {
    private String password;
    private String phone;
}
