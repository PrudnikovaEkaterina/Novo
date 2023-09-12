package ru.dom_novo.dataBase.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.dom_novo.dataBase.DataSourceProvider;

import java.util.List;

public class MarketcallBundleBuildingsDao {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource());

    public static int selectCountFromMarketcallBundleBuildings() {
    return jdbcTemplate.queryForObject("SELECT count(*) from marketcall_bundle_buildings where deleted_at is null",
                Integer.class);
    }
    public static int selectCountDistinctBuildingsFromMarketcallBundleBuildings() {
        return jdbcTemplate.queryForObject("SELECT count(distinct building_id) from marketcall_bundle_buildings where deleted_at is null",
                Integer.class);
    }

    public static List<String> selectTitlesFromMarketcallBundles(){
        return jdbcTemplate.queryForList("SELECT title from marketcall_bundles where deleted_at is null", String.class);
    }

    public static String selectTitleFromMarketcallBundlesForBundle(int external_id){
        return jdbcTemplate.queryForObject("SELECT title from marketcall_bundles where deleted_at is null and external_id=?", String.class, external_id);
    }

    public static List<Integer> selectExternalIdFromMarketcallBundles (){
        return jdbcTemplate.queryForList("SELECT external_id from marketcall_bundles where deleted_at is null", Integer.class);
    }

    public static int selectCountDistinctBuildingsFromMarketcallBundleBuildingsWhereExternalId(int external_id) {
        return jdbcTemplate.queryForObject("SELECT count(distinct bb.building_id) from marketcall_bundle_buildings bb JOIN buildings b ON (bb.building_id=b.id) JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) JOIN  marketcall_bundles mb ON (bb.bundle_id=mb.id) where g.region_code in (50,77) and bb.deleted_at is null and mb.external_id = ?",
                Integer.class, external_id);
    }
    public static List<Integer> selectBuildingIdFromMarketcallBundles (int id){
        return jdbcTemplate.queryForList("SELECT distinct bb.building_id from marketcall_bundle_buildings bb JOIN buildings b ON (bb.building_id=b.id) JOIN gar_ADDRESSOBJECTS g on (b.gar_object_id = g.OBJECTID) JOIN  marketcall_bundles mb ON (bb.bundle_id=mb.id) where g.region_code in (50,77) and bb.deleted_at is null and mb.external_id = ?", Integer.class, id);
    }

}
