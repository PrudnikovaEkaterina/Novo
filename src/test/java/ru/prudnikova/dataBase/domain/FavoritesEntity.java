package ru.prudnikova.dataBase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoritesEntity {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("entity_type")
    private int entityType;
    @JsonProperty("entity_id")
    private int entityId;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
}
