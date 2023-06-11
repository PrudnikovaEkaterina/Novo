package ru.prudnikova.dataBase.managers;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.prudnikova.dataBase.DataSourceProvider;
import ru.prudnikova.dataBase.domain.FavoritesBD;

import java.util.List;
import java.util.stream.Collectors;

public class FavoritesManager {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<Integer> selectEntityIdFromFavoritesForUser(int userId){
        List<FavoritesBD> userFavoritesBDList = jdbcTemplate.query("SELECT entity_id FROM favorites where user_id=? and entity_type=1",
                new BeanPropertyRowMapper<>(FavoritesBD.class, false), userId);
        return userFavoritesBDList.stream().map(FavoritesBD::getEntityId).collect(Collectors.toList());
    }
}
