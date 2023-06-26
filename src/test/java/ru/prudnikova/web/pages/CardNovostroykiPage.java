package ru.prudnikova.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import regexp.RegexpMeth;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class CardNovostroykiPage {

    private final SelenideElement
            CARD_NOVOSTROYKI_PRICE_VALUE = $(".card-novostroyki-price__value"),
            CARD_NOVOSTROYKI_PROFILE_PRICE_VALUE = $$(".card-novostroyki-profile__info-value").first(),
            CARD_SPECIFICATIONS_PARAMETER_VALUE_AREA = $x("//p[text()='Площадь квартир']//following::p[1]");

    public CardNovostroykiPage openZhkPage(int buildingId) {
        open("/zhk/" + buildingId);
        return this;
    }

    public int getPriceValue() {
        sleep(1000);
        String price = CARD_NOVOSTROYKI_PRICE_VALUE.getText();
        return RegexpMeth.extractPrice(RegexpMeth.removeSpacesFromString(price));
    }

    public int getProfilePriceValue() {
        sleep(1000);
        String price = CARD_NOVOSTROYKI_PROFILE_PRICE_VALUE.getText();
        return RegexpMeth.extractPrice(RegexpMeth.removeSpacesFromString(price));
    }

    public double getCardValueAreaMin() {
        sleep(1000);
        String area = CARD_SPECIFICATIONS_PARAMETER_VALUE_AREA.getText();
        List<Double> areaList = RegexpMeth.extractAreaList(area);
        return areaList.get(0);
    }

    public double getCardValueAreaMax() {
        sleep(1000);
        String area = CARD_SPECIFICATIONS_PARAMETER_VALUE_AREA.getText();
        List<Double> areaList = RegexpMeth.extractAreaList(area);
        return areaList.get(1);
    }
}
