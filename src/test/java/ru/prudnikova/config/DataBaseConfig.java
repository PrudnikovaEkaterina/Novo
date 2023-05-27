package ru.prudnikova.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)//аннотация, чтобы указать Owner, что нужно объединить файлы properties
@Config.Sources({ // сообщаем Owner загрузить файлы с раширением .properties, которые находятся в папке resources
        "system:properties",
        "classpath:${env}.properties",
        "file:~/${env}.properties",
        "file:./${env}.properties"
})

public interface DataBaseConfig extends Config {
    @Config.Key("dataSourceUrl")
    @Config.DefaultValue("jdbc:mysql://novo-dom.ru:3306/agency_novo-estate_ru?useSSL=false")
    String dataSourceUrl();

    @Config.Key("dataSourceUser")
    @Config.DefaultValue("4063356894")
    String dataSourceUser();

    @Config.Key("dataSourcePassword")
    @Config.DefaultValue("9AZ3M$YAXiEA6")
    String dataSourcePassword();

}
