package ru.dom_novo.api.models.authModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerModel {
    private Integer id;
    private String phone;
    @JsonProperty("phone_verified_at")
    private Date phoneVerifiedAt;
    private String name;
    @JsonProperty("avatar_path")
    private Object avatarPath;
    private Integer role;
    @JsonProperty("manager_id")
    private Object managerId;
    @JsonProperty("referral_code")
    private String referralCode;
    private String email;
    @JsonProperty("gar_object_id")
    private Integer garObjectId;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private Object manager;
}
