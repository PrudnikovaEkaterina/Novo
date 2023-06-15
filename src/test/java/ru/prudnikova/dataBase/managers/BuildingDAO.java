package ru.prudnikova.dataBase.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public static void selectBuildingReleaseYear() throws IOException {
       List<BuildingEnity> list = jdbcTemplate.query("select * from buildings where id=15054",
                    new BeanPropertyRowMapper<>(BuildingEnity.class, false));
        System.out.println(list);
       String json = String.valueOf(list.get(0).getData_json());
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Object result = JsonPath.read(json,"$.properties.241.values.*");
       String res = objectMapper.writeValueAsString(result);
        Pattern p = Pattern.compile("\\d{4}");
        Matcher m = p.matcher(res);
        int c=0;
        while (m.find()) {
          c = Integer.parseInt(m.group());
        }
        System.out.println(c);






        }

    }

