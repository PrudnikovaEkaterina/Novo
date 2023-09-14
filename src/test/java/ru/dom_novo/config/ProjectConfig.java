package ru.dom_novo.config;

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
    @DefaultValue("https://yadoma-realty.ru")
    String baseUrl();

    @Key("baseUri")
    @DefaultValue("https://yadoma-realty.ru")
    String baseUri();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("browserVersion")
    String browserVersion();

    @Key("browserSize")
    @DefaultValue("1600x800")
    String browserSize();

    @Key("remote")
    String remote();

}



