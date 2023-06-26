package ru.prudnikova.dataBase.managers;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.prudnikova.dataBase.DataSourceProvider;
import ru.prudnikova.dataBase.entities.buildingEntities.BuildingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingDAO {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static String selectBuildingDataJson(int buildingId) {
        List<BuildingEntity> buildingEnityList = jdbcTemplate.query("select * from buildings where id=?",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), buildingId);
        return String.valueOf(buildingEnityList.get(0).getData_json());
    }

    public static List<Integer> selectBuildingIdWithoutFlatsWherePricesSlugContainsRoom() {
        List<BuildingEntity> buildingEnityList = jdbcTemplate.query("select  b.id from buildings b JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) where g.region_code in (50,77) and not exists (select 1 from flats f where f.building_id = b.id and f.status=1) and JSON_EXTRACT (b.data_json, \"$.prices[*].slug\") like '%nb_rooms%' LIMIT 5;",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false));
        return buildingEnityList.stream().map(BuildingEntity::getId).collect(Collectors.toList());
    }

    public static List<Integer> selectBuildingIdWithoutFlats() {
        List<BuildingEntity> buildingEnityList = jdbcTemplate.query("select  buildings.id from buildings JOIN gar_ADDRESSOBJECTS on (buildings.gar_object_id = gar_ADDRESSOBJECTS.OBJECTID) where gar_ADDRESSOBJECTS.region_code in (50,77) and not exists (select 1 from flats where flats.building_id = buildings.id and flats.status=1) and JSON_EXTRACT (buildings.data_json, \"$.prices[*].slug\") like '%sell_nb%' LIMIT 5;",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false));
        System.out.println(buildingEnityList);
        return buildingEnityList.stream().map(BuildingEntity::getId).collect(Collectors.toList());
    }

    public static List<Integer> selectBuildingIdWithoutFlatsWherePricesExistAreaMin() {
        List<BuildingEntity> buildingEnityList = jdbcTemplate.query(" select  * from buildings JOIN gar_ADDRESSOBJECTS on (buildings.gar_object_id = gar_ADDRESSOBJECTS.OBJECTID) where gar_ADDRESSOBJECTS.region_code in (50,77) and not exists (select 1 from flats where flats.building_id = buildings.id and flats.status=1) and JSON_EXTRACT (buildings.data_json, \"$.prices[].area_min\") is not null and JSON_EXTRACT (buildings.data_json, \"$.prices[*].slug\") like '%sell_nb%' LIMIT 5;",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false));
        return buildingEnityList.stream().map(BuildingEntity::getId).collect(Collectors.toList());
    }

    public static List<Integer> selectBuildingIdWithoutFlatsWherePricesExistAreaMax() {
        List<BuildingEntity> buildingEnityList = jdbcTemplate.query(" select  * from buildings JOIN gar_ADDRESSOBJECTS on (buildings.gar_object_id = gar_ADDRESSOBJECTS.OBJECTID) where gar_ADDRESSOBJECTS.region_code in (50,77) and not exists (select 1 from flats where flats.building_id = buildings.id and flats.status=1) and JSON_EXTRACT (buildings.data_json, \"$.prices[].area_max\") is not null and JSON_EXTRACT (buildings.data_json, \"$.prices[*].slug\") like '%sell_nb%' LIMIT 5;",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false));
        return buildingEnityList.stream().map(BuildingEntity::getId).collect(Collectors.toList());
    }

}

