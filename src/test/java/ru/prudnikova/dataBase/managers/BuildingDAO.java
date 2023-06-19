package ru.prudnikova.dataBase.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import regexp.RegexpMeth;
import ru.prudnikova.api.steps.zhkApiSteps.ZhkApi;
import ru.prudnikova.dataBase.DataSourceProvider;
import ru.prudnikova.dataBase.domain.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BuildingDAO {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static String selectBuildingDataJson(int buildingId) {
        List<BuildingEnity> buildingEnityList = jdbcTemplate.query("select * from buildings where id=?",
                new BeanPropertyRowMapper<>(BuildingEnity.class, false), buildingId);
        return String.valueOf(buildingEnityList.get(0).getData_json());
    }

}

