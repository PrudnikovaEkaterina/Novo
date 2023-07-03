package ru.prudnikova.dataBase.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.prudnikova.dataBase.DataSourceProvider;
import ru.prudnikova.dataBase.entities.FlatsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FlatDao {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<FlatsEntity> selectBuildingIdFromFlatsWhereFloorGreaterOrEqualsFloorUnit(int floorUnit) {
        return jdbcTemplate.query("select distinct building_id from flats where floor>=? and status=1",
                new BeanPropertyRowMapper<>(FlatsEntity.class, false), floorUnit);
    }

    public static List<FlatsEntity> selectBuildingIdFromFlatsWithFilterPaymentMethod(String paymentMethod) {
        return jdbcTemplate.query("select distinct building_id from flats where ?=1 and status=1",
                new BeanPropertyRowMapper<>(FlatsEntity.class, false), paymentMethod);
    }

    public static List<FlatsEntity> selectBuildingIdFromFlatsWithFilterRenovation(String renovation) {
        return jdbcTemplate.query("SELECT  distinct building_id FROM flats where finishing=? and status=1",
                new BeanPropertyRowMapper<>(FlatsEntity.class, false), renovation);
    }
}
