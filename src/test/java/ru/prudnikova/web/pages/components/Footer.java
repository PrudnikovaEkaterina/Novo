package ru.prudnikova.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Footer {

    private final SelenideElement
    FOOTER_CONTAINER = $(".one-column-footer__container");

    private final ElementsCollection
    FOOTER_MENU_HEADER = $$(".one-column-footer__menu-header");

    public boolean footerContainerIsVisible(){
        if (FOOTER_CONTAINER.is(Condition.visible))
                return true;
        else return false;
    }

    public void verifyFooterMenuHeader(){
        FOOTER_MENU_HEADER.first().shouldBe(Condition.visible).shouldHave(Condition.text("Услуги"));
        FOOTER_MENU_HEADER.get(1).shouldBe(Condition.visible).shouldHave(Condition.text("Компания"));
        FOOTER_MENU_HEADER.last().shouldBe(Condition.visible).shouldHave(Condition.text("Пользователям"));
    }
}
