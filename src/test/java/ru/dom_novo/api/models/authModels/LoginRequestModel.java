package ru.dom_novo.api.models.authModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequestModel {
    private String password;
    private String phone;
}
