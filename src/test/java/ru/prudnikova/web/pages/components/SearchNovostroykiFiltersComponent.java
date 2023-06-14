package ru.prudnikova.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class SearchNovostroykiFiltersComponent {
    private final SelenideElement
            GEO_SEARCH_FILTER = $(".el-select__input"),
            PRICE_FROM_INPUT = $x("//input[@placeholder='Цена от']"),
            PRICE_TO_INPUT = $x("//input[@placeholder='до']");

    public SearchNovostroykiFiltersComponent setValueInGeoSearchFilter(String value) {
        GEO_SEARCH_FILTER.setValue(value);
        return this;
    }

    public SearchNovostroykiFiltersComponent selectDropdownItem(String searchItemName) {
        $x("//li[@class='el-select-dropdown__item']//span[text()='" + searchItemName + "']").click();
        return this;
    }

    public SearchNovostroykiFiltersComponent selectDropdownDistrict(String searchDistrictName) {
        $x("//li[@class='el-select-dropdown__item']//span[text()='район " + searchDistrictName + "']").click();
        return this;
    }

    public SearchNovostroykiFiltersComponent selectDropdownCity(String searchCityName) {
        $x("//li[@class='el-select-dropdown__item']//span[text()='г. " + searchCityName + "']").click();
        return this;
    }

    public SearchNovostroykiFiltersComponent clickCheckboxFilterRooms(String valueRooms) {
        $$(".el-checkbox-button__inner").findBy(Condition.text(valueRooms)).click();
        return this;
    }

    public SearchNovostroykiFiltersComponent setPriceFrom(String priceFrom) {
        PRICE_FROM_INPUT.setValue(priceFrom);
        return this;
    }

    public SearchNovostroykiFiltersComponent setPriceTo(String priceTo) {
        PRICE_TO_INPUT.setValue(priceTo);
        return this;
    }

}
