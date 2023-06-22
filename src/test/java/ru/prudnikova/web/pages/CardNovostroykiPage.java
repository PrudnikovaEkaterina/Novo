package ru.prudnikova.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import regexp.RegexpMeth;

import static com.codeborne.selenide.Selenide.*;

public class CardNovostroykiPage {

    private final SelenideElement
            CARD_NOVOSTROYKI_PRICE_VALUE = $(".card-novostroyki-price__value"),
            CARD_NOVOSTROYKI_PROFILE_PRICE_VALUE = $$(".card-novostroyki-profile__info-value").first();
    public CardNovostroykiPage openZhkPage(int buildingId) {
        open("/zhk/"+buildingId);
        return this;
    }

    public int getPriceValue() {
       String price = CARD_NOVOSTROYKI_PRICE_VALUE.getText();
        return RegexpMeth.extractPrice(RegexpMeth.removeSpacesFromString(price));
    }

    public int getProfilePriceValue() {
        String price = CARD_NOVOSTROYKI_PROFILE_PRICE_VALUE.getText();
        return RegexpMeth.extractPrice(RegexpMeth.removeSpacesFromString(price));
    }
}
