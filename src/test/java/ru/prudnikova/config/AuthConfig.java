package ru.prudnikova.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)//аннотация, чтобы указать Owner, что нужно объединить файлы properties
@Config.Sources({ // сообщаем Owner загрузить файлы с раширением .properties, которые находятся в папке resources
        "system:properties",
        "classpath:${env}.properties",
        "file:~/${env}.properties",
        "file:./${env}.properties"
})
public interface AuthConfig extends Config {
    @Config.Key("authCookieName")
    @Config.DefaultValue("developer_mode")
    String authCookieName();

    @Config.Key("authCookieValue")
    @Config.DefaultValue("LadcZWB8a15L")
    String authCookieValue();

    @Config.Key("smsCode")
    @Config.DefaultValue("909090")
    String smsCode();


}
