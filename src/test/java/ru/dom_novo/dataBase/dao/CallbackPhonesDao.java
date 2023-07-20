package ru.dom_novo.dataBase.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.dom_novo.dataBase.DataSourceProvider;
import ru.dom_novo.dataBase.entities.CallbackPhonesEntity;
import java.util.List;

public class CallbackPhonesDao {

    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public static List<CallbackPhonesEntity> selectLastEntryFromCallbackPhonesTables(){
        return jdbcTemplate.query("SELECT * FROM callback_phones WHERE id=(SELECT max(id) FROM callback_phones);",
                new BeanPropertyRowMapper<>(CallbackPhonesEntity.class, false));
    }

}
