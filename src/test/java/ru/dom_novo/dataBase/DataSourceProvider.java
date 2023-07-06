package ru.dom_novo.dataBase;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.aeonbits.owner.ConfigCache;
import ru.dom_novo.config.DataBaseConfig;
import javax.sql.DataSource;

public enum DataSourceProvider {
    INSTANCE;

    DataBaseConfig dataBaseConfig = ConfigCache.getOrCreate(DataBaseConfig.class);

    private DataSource dataSource;

    public DataSource getDataSource() {
        if (dataSource == null) {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUrl(dataBaseConfig.dataSourceUrl());
            mysqlDataSource.setUser(dataBaseConfig.dataSourceUser());
            mysqlDataSource.setPassword(dataBaseConfig.dataSourcePassword());
            dataSource = mysqlDataSource;
        }
        return dataSource;
    }
}
