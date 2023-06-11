package ru.prudnikova.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "system:properties",
        "classpath:${env}.properties",
        "file:~/${env}.properties",
        "file:./${env}.properties"
})
public interface AuthConfig extends Config {
    @Config.Key("authCookieName")
    String authCookieName();

    @Config.Key("authCookieValue")
    String authCookieValue();

    @Config.Key("smsCode")
    String smsCode();


}
