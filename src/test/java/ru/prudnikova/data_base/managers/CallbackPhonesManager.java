package ru.prudnikova.data_base.managers;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.prudnikova.data_base.DataSourceProvider;
import ru.prudnikova.data_base.domain.CallbackPhonesBD;
import java.util.List;

public class CallbackPhonesManager {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource()
    );

    public List<CallbackPhonesBD> selectLastEntryFromCallbackPhonesTables(){
        return jdbcTemplate.query("SELECT phone, user_id, link FROM callback_phones WHERE id=(SELECT max(id) FROM callback_phones);",
                new BeanPropertyRowMapper<>(CallbackPhonesBD.class, false));
    }

}
