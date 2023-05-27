package ru.prudnikova.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Footer {
    private final SelenideElement
    FOOTER_CONTAINER = $(".one-column-footer__container");

    public boolean footerContainerIsVisible(){
        if (FOOTER_CONTAINER.is(Condition.visible))
                return true;
        else return false;
    }
}
