package ru.prudnikova.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "system:properties",
        "classpath:${env}.properties",
        "file:~/${env}.properties",
        "file:./${env}.properties"
})

public interface DataBaseConfig extends Config {
    @Config.Key("dataSourceUrl")
    String dataSourceUrl();

    @Config.Key("dataSourceUser")
    String dataSourceUser();

    @Config.Key("dataSourcePassword")
    String dataSourcePassword();

}
