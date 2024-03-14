package ru.dom_novo.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class MoreFiltersModalComponent {
    private final SelenideElement
            MODAL_HEADER_TEXT = $(".simple-modal__header-text"),
            SHOW_BUTTON = $x("//span[text()='Показать объекты']");

    @Step("Check modal header All filters")
    public void verifyModalHeaderText() {
        MODAL_HEADER_TEXT.shouldHave(Condition.text("Все фильтры"));
    }

    @Step("Select check box Housing class")
    public MoreFiltersModalComponent selectCheckboxHousingClass(String housingClass) {
        $$(".el-checkbox-button__inner").findBy(Condition.text(housingClass)).click();
        return this;
    }

    @Step("Click on the Show objects button")
    public MoreFiltersModalComponent clickShowButton() {
        SHOW_BUTTON.click();
        sleep(2000);
        return this;
    }

}
