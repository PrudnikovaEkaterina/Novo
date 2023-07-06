package ru.dom_novo.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:auth.properties"
})
public interface AuthConfig extends Config {
    @Config.Key("authCookieName")
    String authCookieName();

    @Config.Key("authCookieValue")
    String authCookieValue();

    @Config.Key("smsCode")
    String smsCode();


}
