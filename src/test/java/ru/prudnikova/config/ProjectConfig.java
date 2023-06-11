package ru.prudnikova.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:${env}.properties",
        "file:~/${env}.properties",
        "file:./${env}.properties"
})

public interface ProjectConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("https://novo-dom.ru")
    String baseUrl();

    @Key("baseUri")
    @DefaultValue("https://novo-dom.ru")
    String baseUri();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("browserVersion")
    String browserVersion();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("remote")
    String remote();

}



