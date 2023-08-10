package ru.dom_novo.dataBase.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.dom_novo.dataBase.DataSourceProvider;
import ru.dom_novo.dataBase.entities.FlatEntity;

import java.util.List;

public class FlatDao {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<FlatEntity> selectBuildingIdFromFlatsWhereFloorGreaterOrEqualsFloorUnit(int floorUnit) {
        return jdbcTemplate.query("select distinct building_id from flats where floor>=? and status=1",
                new BeanPropertyRowMapper<>(FlatEntity.class, false), floorUnit);
    }

    public static List<FlatEntity> selectBuildingIdFromFlatsWithFilterPaymentMethod(String paymentMethod) {
        return jdbcTemplate.query("select distinct building_id from flats where ?=1 and status=1",
                new BeanPropertyRowMapper<>(FlatEntity.class, false), paymentMethod);
    }

    public static List<FlatEntity> selectBuildingIdFromFlatsWithFilterRenovation(String renovation) {
        return jdbcTemplate.query("SELECT  distinct building_id FROM flats where finishing=? and status=1",
                new BeanPropertyRowMapper<>(FlatEntity.class, false), renovation);
    }
}
