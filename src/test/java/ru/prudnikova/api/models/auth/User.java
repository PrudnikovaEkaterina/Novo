package ru.prudnikova.api.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String phone;
    @JsonProperty("phone_verified_at")
    private Date phoneVerifiedAt;
    private String name;
    @JsonProperty("avatar_path")
    private Object avatarPath;
    private int role;
    private Object manager_id;
    @JsonProperty("referral_code")
    private Object referralCode;
    private String email;
    @JsonProperty("gar_object_id")
    private int garObjectId;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("favorites_flats_count")
    private int favoritesFlatsCount;
    @JsonProperty("favorites_buildings_count")
    private int favoritesBuildingsCount;
    @JsonProperty("recommendations_flats_count")
    private int recommendationsFlatsCount;
    @JsonProperty("recommendations_buildings_count")
    private int recommendationsBuildingsCount;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private Object manager;
}
