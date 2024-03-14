package ru.dom_novo.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class SearchNovostroykiFiltersComponent {
    private final SelenideElement
            GEO_SEARCH_FILTER = $(".el-select__input"),
            PRICE_FROM_INPUT = $x("//input[@placeholder='Цена от']"),
            PRICE_TO_INPUT = $x("//input[@placeholder='до']");

    @Step("Set value in the filter field Search by geo")
    public SearchNovostroykiFiltersComponent setValueInGeoSearchFilter(String value) {
        GEO_SEARCH_FILTER.setValue(value);
        return this;
    }

    @Step("Select from dropdown list {searchItemName}")
    public void selectDropdownItem(String searchItemName) {
        $x("//li[@class='el-select-dropdown__item']//span[text()='" + searchItemName + "']").click();
    }

    @Step("Select from dropdown list {searchDistrictName}")
    public void selectDropdownDistrict(String searchDistrictName) {
        $x("//li[@class='el-select-dropdown__item']//span[text()='район " + searchDistrictName + "']").click();
    }

    @Step("Select from dropdown list {searchCityName}")
    public void selectDropdownCity(String searchCityName) {
        $x("//li[@class='el-select-dropdown__item']//span[text()='г. " + searchCityName + "']").click();
    }

    @Step("Select check box {valueRooms}")
    public void clickCheckboxFilterRooms(String valueRooms) {
        $$(".el-checkbox-button__inner").findBy(Condition.text(valueRooms)).click();
    }

    @Step("Set value {priceFrom} in the filter field Price from")
    public void setPriceFrom(String priceFrom) {
        PRICE_FROM_INPUT.setValue(priceFrom);
    }

    @Step("Set value {priceTo} in the filter field Price to")
    public void setPriceTo(String priceTo) {
        PRICE_TO_INPUT.setValue(priceTo);
    }

}
