package ru.dom_novo.web.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardNovostroykiPage {

    private final SelenideElement
            CARD_NOVOSTROYKI_PRICE_VALUE = $(".card-novostroyki-price__value"),
            CARD_NOVOSTROYKI_PROFILE_PRICE_VALUE = $$(".card-novostroyki-profile__info-value").first(),
            CARD_SECTION_DOCUMENT_TITLE = $x("//h2[text()='Документация']"),
            CARD_DOCUMENTS_SHOW_MORE_BUTTON = $(".card-documents__show-more");


    private final ElementsCollection
            CARD_DOCUMENTS = $$(".card-documents__document"),
            CARD_DOCUMENTS_TITLE = $$(".card-documents__document-title");

    @Step("Open page zhk with id {buildingId}")
    public CardNovostroykiPage open(int buildingId) {
        Selenide.open("/zhk/" + buildingId);
        return this;
    }

    @Step("Check for the Documentation title on the page")
    public void checkCardSectionDocumentTitle() {
        CARD_SECTION_DOCUMENT_TITLE.shouldBe(visible);
    }

    @Step("Check for the Show more button in the Documentation section")
    public boolean checkVisibleCardDocumentsShowMoreButton() {
        return CARD_DOCUMENTS_SHOW_MORE_BUTTON.is(visible);
    }

    @Step("Get a list of all titles in the Documentation section")
    public List<String> getCardDocumentsTitle() {
        return CARD_DOCUMENTS_TITLE.texts();
    }

    @Step("Check document list size")
    public void checkCardDocumentsListSize(int expectedSize) {
        CARD_DOCUMENTS.shouldBe(CollectionCondition.size(expectedSize));
    }

    @Step("Click on the Show more button")
    public void clickCardDocumentsShowMoreButton() {
        CARD_DOCUMENTS_SHOW_MORE_BUTTON.click();
    }

    @Step("Get price value")
    public String getPriceValue() {
        sleep(1000);
        return CARD_NOVOSTROYKI_PRICE_VALUE.getText();
    }

    @Step("Get profile price value")
    public String getProfilePriceValue() {
        sleep(1000);
        return CARD_NOVOSTROYKI_PROFILE_PRICE_VALUE.getText();
    }
}
