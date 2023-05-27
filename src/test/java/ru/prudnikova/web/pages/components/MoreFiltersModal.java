package ru.prudnikova.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MoreFiltersModal {
    private final SelenideElement
            MODAL_HEADER_TEXT = $(".simple-modal__header-text"),
            SHOW_BUTTON = $x("//span[text()='Показать объекты']");

    public void verifyModalHeaderText() {
        MODAL_HEADER_TEXT.shouldHave(Condition.text("Все фильтры"));
    }


    public MoreFiltersModal clickCheckboxHousingClass(String housingClass) {
        $$(".el-checkbox-button__inner").findBy(Condition.text(housingClass)).click();
        return this;
    }

    public MoreFiltersModal clickShowButton() {
        SHOW_BUTTON.click();
        sleep(2000);
        return this;
    }

}
