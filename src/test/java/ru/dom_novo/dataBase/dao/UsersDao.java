package ru.dom_novo.dataBase.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.dom_novo.dataBase.DataSourceProvider;

public class UsersDao {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.INSTANCE.getDataSource());

    public static int selectUserId (String userPhoneNumber) {
        return jdbcTemplate.queryForObject("select id from users where phone=?", Integer.class, userPhoneNumber);
    }
}
