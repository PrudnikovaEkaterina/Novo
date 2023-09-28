package ru.dom_novo.dataBase.dao;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public static String selectBuildingReleaseDate(int id) {
        String releaseYear = jdbcTemplate.queryForObject("select JSON_VALUE (data_json, \"$.properties.204.values.*\") from buildings where id=?;",
                String.class, id);
        if (releaseYear!=null)
            return releaseYear;
        else
            return null;
    }
    public static List<Integer> selectDistinctHouseId(int building_id){
        return jdbcTemplate.queryForList("select  distinct house_id from flats where building_id=? and status=1", Integer.class, building_id);
    }
    public static List<Integer> selectAllHouseId(int building_id){
        return jdbcTemplate.queryForList("select id from buildings where parent_id = ?", Integer.class, building_id);
    }

    public static List<Integer> selectDistinctBuildingIdFromFlats() {
        return jdbcTemplate.queryForList("SELECT DISTINCT f.building_id from flats f JOIN buildings b ON f.building_id=b.id JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) where g.region_code in (50,77) and f.status=1", Integer.class);
    }

    public static List<String> selectDistinctBuildingTitleEng() {
        return jdbcTemplate.queryForList("SELECT DISTINCT b.title_eng from buildings b JOIN flats f ON f.building_id=b.id JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) where g.region_code in (50,77) and f.status=1", String.class);
    }

    public static List<Integer> selectDistinctBuildingGarObjectId() {
        return jdbcTemplate.queryForList("SELECT DISTINCT b.gar_object_id from buildings b JOIN flats f ON f.building_id=b.id JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) where g.region_code in (50,77) and f.status=1", Integer.class);
    }
    public static List<String> selectPathFromGarMunHierarchy(int garObjectId) {
        return jdbcTemplate.queryForList("select  PATH from gar_MUN_HIERARCHY where OBJECTID=?", String.class, garObjectId);
    }
    public static String selectTitleEngFromGarAddressObjects(String garObjectId) {
//        обернула в блок try/catch так как запрос может не возвращать title_eng (не выполнится условие level<=5),
//        чтобы избежать EmptyResultDataAccessException и возвращать null
        try {
            return jdbcTemplate.queryForObject("select title_eng from gar_ADDRESSOBJECTS where OBJECTID=? and level<=5", String.class, garObjectId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public static int selectCountFromFlatsWhereHouseIdIsNull (int buildingId) {
        return jdbcTemplate.queryForObject("select count(*) from flats where building_id = ? and status=1 and house_id is null;", Integer.class, buildingId);
    }
    public static List<Integer> selectDistinctBuildingIdWithFlats() {
        return jdbcTemplate.queryForList("select distinct b.id from buildings b JOIN flats f ON(b.id=f.building_id) JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) where g.region_code in (50,77) and f.status=1", Integer.class);
    }
    public static List<Integer> selectDistinctBuildingIdWithPrices(){
        return jdbcTemplate.queryForList("select b.id from buildings b JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) where not exists (select 1 from flats f where f.building_id = b.id and f.status=1) and JSON_VALUE(data_json, \"$.prices[*].*\") is not null and g.region_code in (50,77) and b.parent_id is null;", Integer.class);
    }
    public static int selectCountAllFromFlatsWhereBuildingIdIsValueAndStatusIs1 (int buildingId){
        return jdbcTemplate.queryForObject("select count(*) from flats where building_id=? and status=1", Integer.class, buildingId);
    }

    public static int selectCountAllFromFlatsWhereBuildingIdIsValueAndStatusIs1WithFilterPrice (int buildingId, int priceMin, int priceMax){
        return jdbcTemplate.queryForObject("select count(*) from flats where building_id=? and status=1 and price_total >= ? and price_total <= ?", Integer.class, buildingId, priceMin, priceMax);
    }

    public static int selectCountAllFromFlatsWhereBuildingIdIsValueAndStatusIs1WithFilterArea (int buildingId, int areaMin, int areaMax){
        return jdbcTemplate.queryForObject("select count(*) from flats where building_id=? and status=1 and area_total >= ? and area_total <= ?", Integer.class, buildingId, areaMin, areaMax);
    }

    public static int selectCountAllFromFlatsWhereBuildingIdIsValueAndStatusIs1WithFilterFloor (int buildingId, int floorMin, int floorMax){
        return jdbcTemplate.queryForObject("select count(*) from flats where building_id=? and status=1 and floor >= ? and floor <= ?", Integer.class, buildingId, floorMin, floorMax);
    }

}

