package ru.dom_novo.dataBase.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.dom_novo.dataBase.DataSourceProvider;
import ru.dom_novo.dataBase.entities.buildingEntities.BuildingEntity;

import java.util.List;

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

    public static List<BuildingEntity> selectAllFromBuildingsWhereParentIdIs(int parentId) {
        return jdbcTemplate.query("select * from buildings where parent_id=?;",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), parentId);
    }

    public static int selectBuildingReleaseYear(int id) {
        String releaseYear = jdbcTemplate.queryForObject("select JSON_VALUE (data_json, \"$.properties.241.values.*\") from buildings where id=?;",
                String.class, id);
        if (releaseYear!=null)
            return Integer.parseInt(releaseYear);
        else
            return 0;
    }
}

