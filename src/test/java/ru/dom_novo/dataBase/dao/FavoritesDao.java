package ru.dom_novo.dataBase.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.dom_novo.dataBase.DataSourceProvider;
import ru.dom_novo.dataBase.entities.FavoritesEntity;
import ru.dom_novo.dataBase.entities.buildingEntities.BuildingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FavoritesDao {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<Integer> selectFromFavoritesBuildingsIdForUser(int userId){
        List<FavoritesEntity> userFavoritesBDList = jdbcTemplate.query("SELECT entity_id FROM favorites where user_id=? and entity_type=1",
                new BeanPropertyRowMapper<>(FavoritesEntity.class, false), userId);
        return userFavoritesBDList.stream().map(FavoritesEntity::getEntityId).collect(Collectors.toList());
    }

    public static List<String> selectFavoritesBuildingsSortPriceDesc(int userId){
        List<BuildingEntity> list = jdbcTemplate.query("select b.title_eng, min(fl.price_total) from flats fl JOIN favorites f ON (f.entity_id=fl.building_id) JOIN buildings b ON (f.entity_id=b.id) where f.user_id=? and f.entity_type=1 and  fl.status=1 group by fl.building_id order by min(fl.price_total) desc",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), userId);
        return list.stream().map(BuildingEntity::getTitle_eng).collect(Collectors.toList());
    }

    public static List<String> selectFavoritesBuildingsSortPriceAsc(int userId){
        List<BuildingEntity> list = jdbcTemplate.query("select b.title_eng, min(fl.price_total) from flats fl JOIN favorites f ON (f.entity_id=fl.building_id) JOIN buildings b ON (f.entity_id=b.id) where f.user_id=? and f.entity_type=1 and  fl.status=1 group by fl.building_id order by min(fl.price_total) asc",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), userId);
        return list.stream().map(BuildingEntity::getTitle_eng).collect(Collectors.toList());
    }

    public static List<String> selectFavoritesBuildingsSortAreaAsc(int userId){
        List<BuildingEntity> list = jdbcTemplate.query("select b.title_eng, min(fl.area_total) from flats fl JOIN favorites f ON (f.entity_id=fl.building_id) JOIN buildings b ON (f.entity_id=b.id) where f.user_id=? and f.entity_type=1 and  fl.status=1 group by fl.building_id order by min(fl.area_total) asc",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), userId);
        return list.stream().map(BuildingEntity::getTitle_eng).collect(Collectors.toList());
    }

    public static List<String> selectFavoritesBuildingsSortAreaDesc(int userId){
        List<BuildingEntity> list = jdbcTemplate.query("select b.title_eng, min(fl.area_total) from flats fl JOIN favorites f ON (f.entity_id=fl.building_id) JOIN buildings b ON (f.entity_id=b.id) where f.user_id=? and f.entity_type=1 and  fl.status=1 group by fl.building_id order by min(fl.area_total) desc",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), userId);
        return list.stream().map(BuildingEntity::getTitle_eng).collect(Collectors.toList());
    }

    public static List<String> selectFavoritesBuildingsSortPriceM2Asc(int userId){
        List<BuildingEntity> list = jdbcTemplate.query("select b.title_eng, min(fl.price_m2) from flats fl JOIN favorites f ON (f.entity_id=fl.building_id) JOIN buildings b ON (f.entity_id=b.id) where f.user_id=? and f.entity_type=1 and  fl.status=1 group by fl.building_id order by min(fl.price_m2) asc",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), userId);
        return list.stream().map(BuildingEntity::getTitle_eng).collect(Collectors.toList());
    }

    public static List<String> selectFavoritesBuildingsSortPriceM2Desc(int userId){
        List<BuildingEntity> list = jdbcTemplate.query("select b.title_eng, min(fl.price_m2) from flats fl JOIN favorites f ON (f.entity_id=fl.building_id) JOIN buildings b ON (f.entity_id=b.id) where f.user_id=? and f.entity_type=1 and  fl.status=1 group by fl.building_id order by min(fl.price_m2) desc",
                new BeanPropertyRowMapper<>(BuildingEntity.class, false), userId);
        return list.stream().map(BuildingEntity::getTitle_eng).collect(Collectors.toList());
    }

    public static List<String> selectFavoritesBuildingsUpdatedAt(int userId){
        return jdbcTemplate.queryForList("select DATE_FORMAT(f.updated_at, '%d.%m.%Y') from favorites f JOIN flats fl ON (f.entity_id=fl.building_id) JOIN buildings b ON (f.entity_id=b.id) where f.user_id=? and f.entity_type=1 and  fl.status=1 group by fl.building_id order by min(fl.price_total) asc", String.class, userId);
    }

}
