package ru.prudnikova.data_base.managers;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.prudnikova.data_base.DataSourceProvider;
import ru.prudnikova.data_base.domain.CallbackPhonesBD;
import ru.prudnikova.data_base.domain.UserFavoritesBD;

import java.util.List;
import java.util.stream.Collectors;

public class UserFavoritesManager {

    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<Integer> selectEntityIdFromFavoritesForUser(int userId){
        List<UserFavoritesBD> userFavoritesBDList = jdbcTemplate.query("SELECT entity_id FROM favorites where user_id=? and entity_type=1",
                new BeanPropertyRowMapper<>(UserFavoritesBD.class, false), userId);
        return userFavoritesBDList.stream().map(UserFavoritesBD::getEntityId).collect(Collectors.toList());
    }
}
