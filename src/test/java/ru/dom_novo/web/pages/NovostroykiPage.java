package ru.dom_novo.web.pages;

import com.codeborne.selenide.*;
import ru.dom_novo.regexp.RegexpMeth;
import ru.dom_novo.web.pages.components.CallMeWidgetComponent;
import ru.dom_novo.web.pages.components.FooterComponent;
import ru.dom_novo.web.pages.components.MoreFiltersModalComponent;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;

public class NovostroykiPage {

    private final SelenideElement
            SEARCH_ITEM_TITLE_TEXT = $(".search-item__title-text"),
            MORE_FILTERS_BUTTON = $(".more-filters__button-text"),
            NOTIFICATION_INDICATOR = $x("//a[@class='notification-indicator']"),
            SEARCH_ITEM_CONTENT_FIRST = $$(".search-item__content").first(),
            SEARCH_ITEM_CONTENT_LAST = $$(".search-item__content").last(),
            CALL_ME_WIDGET_BUTTON_ICON = $x("//div[@class='call-me-widget search-item__call-me'][1]"),
            SEARCH_NOVOSTROYKI_CONTENT_TOTAL = $(".search-novostroyki-content__total"),
            SEARCH_PRICE_LIST_PRICE = $(".search-price-list__price"),
            SEARCH_ITEM_TOTAL_AREA =$(".search-item__total-area");

    private final ElementsCollection
            SEARCH_ITEM_ADDRESS = $$(".search-item__address"),
            SEARCH_ITEM_DEVELOPER_TEXT = $$(".search-item__developer-text"),
            SEARCH_PRICE_LIST_TABLE = $$(".search-price-list__table"),
            TAG = $$(".base-round-link-button__text"),
            SEARCH_FILTERS_TAGS = $$(".search-filters__tags");

    MoreFiltersModalComponent moreFiltersModal = new MoreFiltersModalComponent();
    CallMeWidgetComponent callMeWidget = new CallMeWidgetComponent();
    FooterComponent footer = new FooterComponent();

    public void openNovostroykiPage() {
        open("/novostroyki");
    }

    public void openNovostroykiPageWithFilterNoFlatsAndBuildingId(int buildingId) {
        open("/novostroyki?buildings=" + buildingId + "&no_flats=1");
    }


    public void verifySearchBuildingTitleText(String title) {
        SEARCH_ITEM_TITLE_TEXT.shouldHave(Condition.text(title));
    }

    public void verifyResultSearchBuildingContent(String content) {
        sleep(1000);
        SEARCH_ITEM_ADDRESS.shouldBe(CollectionCondition.allMatch("all elements contains text", el -> el.getText().contains(content)));
    }

    public void verifyResultSearchBuildingDeveloper(String developer) {
        SEARCH_ITEM_DEVELOPER_TEXT.shouldBe(CollectionCondition.allMatch("all elements contains text", el -> el.getText().contains(developer)));
    }

    public void verifyResultSearchByFilterRooms(String rooms) {
        SEARCH_PRICE_LIST_TABLE.shouldBe(CollectionCondition.allMatch("all elements contains text", el -> el.getText().contains(rooms)));
    }

    public void verifyTagVisible(String tagName) {
        TAG.findBy(Condition.text(tagName)).shouldBe(Condition.visible);
    }

    public MoreFiltersModalComponent openMoreFiltersModal() {
        MORE_FILTERS_BUTTON.click();
        moreFiltersModal.verifyModalHeaderText();
        return moreFiltersModal;
    }

    public int getSearchNovostroykiContentTotal() {
        String[] mas = SEARCH_NOVOSTROYKI_CONTENT_TOTAL.getText().split(" ");
        return Integer.parseInt(mas[1]);
    }

    public void verifyResultSearchByFilterHousingClass(String housingClass) {
        String[] mas = SEARCH_NOVOSTROYKI_CONTENT_TOTAL.getText().split(" ");
        System.out.println(Integer.parseInt(mas[1]));
        if (Integer.parseInt(mas[1]) <= 15)
            $$(".search-item-slider__tags").filter(Condition.text(housingClass)).shouldHave(size(Integer.parseInt(mas[1])));
        else
            $$(".search-item-slider__tags").filter(Condition.text(housingClass)).shouldHave(size(15));

    }

    public void verifyNotificationIndicator(String numberOfFiltersSelected) {
        NOTIFICATION_INDICATOR.shouldHave(Condition.text(numberOfFiltersSelected));
    }

    public NovostroykiPage hoverSearchItemContent() {
        SEARCH_ITEM_CONTENT_FIRST.hover();
        return this;
    }

    public CallMeWidgetComponent openCallMeWidget() {
        CALL_ME_WIDGET_BUTTON_ICON.shouldBe(Condition.visible).click();
        return callMeWidget;
    }

    public void scrollNovostroykiItemsToLastPage() {
        boolean b = true;
        while (b) {
            actions().moveToElement(SEARCH_ITEM_CONTENT_LAST).build().perform();
//          Selenide.executeJavaScript("document.querySelector('.infinity-scroll__viewport').scrollBy(0,1000)"); //было так, пока не изменили скролл на сайте
            Selenide.executeJavaScript("window.scrollBy(0,500)");
            sleep(1000);
            if (footer.footerContainerIsVisible())
                b = false;
        }
    }
    public double getRoomPriceValue() {
        sleep(1000);
        String price = SEARCH_PRICE_LIST_PRICE.getText();
        return RegexpMeth.extractPriceOrAreaDouble(price);
    }
    public int getPricePerSquareValue() {
        String price = SEARCH_ITEM_TOTAL_AREA.getText();
       String priceWithoutSpase = RegexpMeth.removeSpacesFromString(price);
       return RegexpMeth.extractPrice(priceWithoutSpase);
    }

    public double getRoomPriceValue(String room) {
        sleep(1000);
        String price = $x("//td[text()='"+room+"']/following-sibling::td[2]").getText();
        return RegexpMeth.extractPriceOrAreaDouble(price);
    }

    public String getRoomAreaValue(String room) {
        sleep(1000);
        String area =  $x("//td[text()='"+room+"']/following-sibling::td[1]").getText();
        return RegexpMeth.extractPriceOrAreaDouble1(area);
    }
    public String getTextFromSearchFiltersTags(){
      return   SEARCH_FILTERS_TAGS.first().getText();
    }

}

