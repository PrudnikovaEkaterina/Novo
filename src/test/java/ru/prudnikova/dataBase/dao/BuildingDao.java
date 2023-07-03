package ru.prudnikova.dataBase.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.prudnikova.dataBase.DataSourceProvider;
import ru.prudnikova.dataBase.entities.buildingEntities.BuildingEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingDao {

    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource());

    public static String selectBuildingDataJson(int buildingId) {
        List<BuildingEntity> buildingEnityList = jdbcTemplate.query("select * from buildings where id=?",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), buildingId);
        return String.valueOf(buildingEnityList.get(0).getData_json());
    }

    public static List<BuildingEntity> selectBuildingIdWithoutFlatsWherePricesSlugExistRoom(String slugRoom) {
        return jdbcTemplate.query("select b.id from buildings b JOIN gar_ADDRESSOBJECTS g " +
                        "on (b.gar_object_id = g.OBJECTID) " +
                        "where g.region_code in (50,77) and not exists (select 1 from flats f where f.building_id = b.id and f.status=1) " +
                        "and JSON_EXTRACT (b.data_json, \"$.prices[*].slug\") like ? LIMIT 5;",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), slugRoom);
    }

    public static List<BuildingEntity> selectBuildingIdWithoutFlatsWherePricesExistUnitPriceMin() {
        return jdbcTemplate.query("select DISTINCT b.id from buildings b " +
                        "JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) where g.region_code in (50,77) " +
                        "and not exists (select 1 from flats f where f.building_id = b.id and f.status=1) " +
                        "and JSON_VALUE (b.data_json, \"$.prices[*].slug\") like 'sell_nb' " +
                        "and JSON_VALUE (b.data_json, \"$.prices[*].unit_price_min\") is not null LIMIT 5;",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false));
    }

    public static List<BuildingEntity> selectBuildingIdWithoutFlatsWherePricesExistAreaMin() {
        return jdbcTemplate.query("select  b.id from buildings b " +
                        "JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) " +
                        "where g.region_code in (50,77) and not exists (select 1 from flats f where f.building_id = b.id and f.status=1) " +
                        "and JSON_EXTRACT (b.data_json,\"$.prices[*].area_min\") is not null " +
                        "and JSON_EXTRACT (b.data_json, \"$.prices[*].slug\") like '%sell_nb%' LIMIT 5;",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false));
    }

}

