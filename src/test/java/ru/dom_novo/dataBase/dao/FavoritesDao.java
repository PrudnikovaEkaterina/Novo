package ru.dom_novo.dataBase.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.dom_novo.dataBase.DataSourceProvider;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesDao {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<Integer> selectBuildingIdFromFavorites(int userId){
        return jdbcTemplate.queryForList("SELECT entity_id FROM favorites where user_id=? and entity_type=1", Integer.class, userId);
    }

    public static Map<Integer,String> select(int userId){
        return jdbcTemplate.query("select entity_id, DATE_FORMAT(updated_at, '%d.%m.%Y') from favorites where user_id=? and entity_type=1", (ResultSet rs) -> {
            HashMap<Integer, String> results = new HashMap<>();
            while (rs.next()) {
                results.put(rs.getInt("entity_id"), rs.getString("DATE_FORMAT(updated_at, '%d.%m.%Y')"));
            }
            return results;
        }, userId);
    }

}
