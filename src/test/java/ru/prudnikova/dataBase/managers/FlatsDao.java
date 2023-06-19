package ru.prudnikova.dataBase.managers;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.prudnikova.dataBase.DataSourceProvider;
import ru.prudnikova.dataBase.domain.FlatsEntity;
import java.util.List;
import java.util.stream.Collectors;

public class FlatsDao {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<Integer> selectBuildingIdFromFlatsWhereFloorGreaterOrEqualsFloorUnit(int floorUnit){
        List<FlatsEntity> flatsBDList = jdbcTemplate.query("select distinct building_id from flats where floor>=? and status=1",
                new BeanPropertyRowMapper<>(FlatsEntity.class, false), floorUnit);
       return flatsBDList.stream().map(FlatsEntity::getBuildingId).collect(Collectors.toList());
    }

    public static List<Integer> selectBuildingIdFromFlatsWithFilterPaymentMethod(String paymentMethod){
        List<FlatsEntity> flatsBDList = jdbcTemplate.query("select distinct building_id from flats where ?=1 and status=1",
                new BeanPropertyRowMapper<>(FlatsEntity.class, false), paymentMethod);
        return flatsBDList.stream().map(FlatsEntity::getBuildingId).collect(Collectors.toList());

    }

    public static List<Integer> selectBuildingIdFromFlatsWithFilterRenovation(String renovation){
        List<FlatsEntity> flatsBDList = jdbcTemplate.query("SELECT  distinct building_id FROM flats where finishing=? and status=1",
                new BeanPropertyRowMapper<>(FlatsEntity.class, false), renovation);
        return flatsBDList.stream().map(FlatsEntity::getBuildingId).collect(Collectors.toList());

    }
}
