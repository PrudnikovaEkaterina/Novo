package ru.dom_novo.web.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import regexp.RegexpMeth;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardNovostroykiPage {

    private final SelenideElement
            CARD_NOVOSTROYKI_PRICE_VALUE = $(".card-novostroyki-price__value"),
            CARD_NOVOSTROYKI_PROFILE_PRICE_VALUE = $$(".card-novostroyki-profile__info-value").first(),
            CARD_SPECIFICATIONS_PARAMETER_VALUE_AREA = $x("//p[text()='Площадь квартир']//following::p[1]"),
            CARD_SECTION_DOCUMENT_TITLE= $x("//h2[text()='Документация']"),
            CARD_DOCUMENTS_SHOW_MORE_BUTTON=$(".card-documents__show-more");


    private final ElementsCollection
            CARD_DOCUMENTS = $$(".card-documents__document"),
            CARD_DOCUMENTS_TITLE=$$(".card-documents__document-title");

    public CardNovostroykiPage openCard(int buildingId) {
        open("/zhk/" + buildingId);
        return this;
    }
    public CardNovostroykiPage checkCardSectionDocumentTitle() {
       CARD_SECTION_DOCUMENT_TITLE.shouldBe(visible);
        return this;
    }

    public boolean checkVisibleCardDocumentsShowMoreButton() {
        return CARD_DOCUMENTS_SHOW_MORE_BUTTON.is(visible);
    }

    public CardNovostroykiPage hoverCardSectionDocumentTitle() {
        CARD_SECTION_DOCUMENT_TITLE.hover();
        return this;
    }

    public List<String> getCardDocumentsTitle() {
        return CARD_DOCUMENTS_TITLE.texts();
    }


    public CardNovostroykiPage checkCardDocumentsListSize(int expectedSize) {
        CARD_DOCUMENTS.shouldBe(CollectionCondition.size(expectedSize));
        return this;
    }


    public CardNovostroykiPage checkCardDocumentsShowMoreButton() {
       CARD_DOCUMENTS_SHOW_MORE_BUTTON.shouldBe(visible);
        return this;
    }

    public CardNovostroykiPage clickCardDocumentsShowMoreButton() {
        CARD_DOCUMENTS_SHOW_MORE_BUTTON.click();
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
