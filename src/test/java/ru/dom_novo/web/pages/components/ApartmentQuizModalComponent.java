package ru.dom_novo.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class ApartmentQuizModalComponent {

    private final SelenideElement
            APARTMENT_QUIZ_MODAL_TITLE = $(".apartment-quiz-modal__title");

    @Step("Check the title in the modal apartment quiz")
    public void checkApartmentQuizModalTitle() {
        String apartmentQuizModalTitle = "Бесплатно подберем квартиру по вашим критериям!";
        APARTMENT_QUIZ_MODAL_TITLE
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text(apartmentQuizModalTitle));
    }
}
