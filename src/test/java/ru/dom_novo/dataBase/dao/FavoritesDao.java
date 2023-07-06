package ru.dom_novo.dataBase.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.dom_novo.dataBase.DataSourceProvider;
import ru.dom_novo.dataBase.entities.FavoritesEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FavoritesDao {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<Integer> selectEntityIdFromFavoritesForUser(int userId){
        List<FavoritesEntity> userFavoritesBDList = jdbcTemplate.query("SELECT entity_id FROM favorites where user_id=? and entity_type=1",
                new BeanPropertyRowMapper<>(FavoritesEntity.class, false), userId);
        return userFavoritesBDList.stream().map(FavoritesEntity::getEntityId).collect(Collectors.toList());
    }
}
