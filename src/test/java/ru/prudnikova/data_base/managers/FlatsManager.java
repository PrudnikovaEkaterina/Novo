package ru.prudnikova.data_base.managers;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.prudnikova.data_base.DataSourceProvider;
import ru.prudnikova.data_base.domain.FlatsBD;
import java.util.List;
import java.util.stream.Collectors;

public class FlatsManager {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<Integer> selectBuildingIdFromFlatsWhereFloorGreaterOrEqualsFloorUnit(int floorUnit){
        List<FlatsBD> flatsBDList = jdbcTemplate.query("select distinct building_id from flats where floor>=? and status=1",
                new BeanPropertyRowMapper<>(FlatsBD.class, false), floorUnit);
       return flatsBDList.stream().map(FlatsBD::getBuildingId).collect(Collectors.toList());
    }

    public static List<Integer> selectBuildingIdFromFlatsWherePaymentMethodIsMortgage(String paymentMethod){
        List<FlatsBD> flatsBDList = jdbcTemplate.query("select distinct building_id from flats where ?=1 and status=1",
                new BeanPropertyRowMapper<>(FlatsBD.class, false), paymentMethod);
        return flatsBDList.stream().map(FlatsBD::getBuildingId).collect(Collectors.toList());

    }
}
