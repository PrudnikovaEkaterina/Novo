package ru.dom_novo.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:db.properties"
})

public interface DataBaseConfig extends Config {
    @Config.Key("dataSourceUrl")
    String dataSourceUrl();

    @Config.Key("dataSourceUser")
    String dataSourceUser();

    @Config.Key("dataSourcePassword")
    String dataSourcePassword();

}
