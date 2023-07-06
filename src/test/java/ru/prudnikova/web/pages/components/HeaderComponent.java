package ru.prudnikova.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class HeaderComponent {
    ApartmentQuizModalComponent apartmentQuizModal = new ApartmentQuizModalComponent();

    private final SelenideElement
            NOVOSTROYKI_LINK = $x("//a[text()='Новостройки']"),
            APARTMENT_QUIZ_MODAL_LINK = $x("//a[text()='Помощь в подборе']"),
            ABOUT_LINK = $("[href='/about']"),
            CONTACTS_LINK = $("[href='/contacts']"),
            SIGN_IN_LINK = $x("//span[text()='Войти']"),
            HEADER_ACCOUNT_TEXT = $(".one-column-header__account-text"),
            USER_MENU_DROPDOWN_TEXT_PROFILE = $$(".user-menu__dropdown-text").first(),
            USER_MENU_DROPDOWN_TEXT_EXIT = $$(".user-menu__dropdown-text").last(),
            HEADER_LOGO = $(".one-column-header__logo"),
            FAVORITES_ICON = $x("//*[@href='/build/desktop/sprite.11fcc1b0.svg#favorite-2-usage']"),
            FAVORITES_TEXT = $(".favorites-menu-dropdown__favorites-text"),
            FAVORITES_COUNTER = $(".favorites-menu-dropdown__favorites-counter"),
            ACCOUNT_ICON = $x("//*[@href='/build/desktop/sprite.11fcc1b0.svg#account-2-usage']");

    private final ElementsCollection
            FAVORITES_MENU_DROPDOWN = $$(".favorites-menu-dropdown__dropdown-item");

    public HeaderComponent followingNovostroykiLink() {
        NOVOSTROYKI_LINK.click();
        sleep(2000);
        return this;
    }

    public void verifyUrlAfterFollowingNovostroykiLink() {
        Assertions.assertEquals(baseUrl + "/novostroyki", url());
    }


    public HeaderComponent followingApartmentQuizModalLink() {
        APARTMENT_QUIZ_MODAL_LINK.click();
        sleep(2000);
        return this;
    }

    public void verifyApartmentQuizModalTitle() {
        apartmentQuizModal.verifyApartmentQuizModalTitle();
    }

    public HeaderComponent followingAboutLink() {
        ABOUT_LINK.click();
        sleep(1000);
        return this;
    }

    public void verifyUrlFollowingAboutLink() {
        Assertions.assertEquals(baseUrl + "/about", url());
    }

    public HeaderComponent followingContactsLink() {
        CONTACTS_LINK.click();
        sleep(1000);
        return this;
    }

    public void verifyUrlFollowingContactsLink() {
        Assertions.assertEquals(baseUrl + "/contacts", url());
    }


    public HeaderComponent followingSingInLink() {
        SIGN_IN_LINK.click();
        sleep(1000);
        return this;
    }

    public void verifyUrlFollowingSingInLink() {
        Assertions.assertEquals(baseUrl + "/auth", url());
    }

    public void hoverHeaderAccountIconAndCheckUserMenuDropdownText() {
        HEADER_ACCOUNT_TEXT.hover();
        USER_MENU_DROPDOWN_TEXT_PROFILE.shouldHave(Condition.text("Профиль"));

    }

    public void checkAccountName(String userName) {
        HEADER_ACCOUNT_TEXT.shouldHave(Condition.text(userName));
    }

    public void logout() {
        HEADER_ACCOUNT_TEXT.hover();
        USER_MENU_DROPDOWN_TEXT_EXIT.click();
        HEADER_ACCOUNT_TEXT.shouldHave(Condition.text("Войти"));
    }

    public HeaderComponent clickHeaderLogo() {
       HEADER_LOGO.click();
       sleep(500);
       return this;
    }

    public void checkGoToMainPageAfterClickHeaderLogo() {
        Assertions.assertEquals(baseUrl + "/", url());
    }

    public HeaderComponent checkVisibleSignInLinkForUnauthorizedUser() {
        SIGN_IN_LINK.shouldBe(Condition.visible);
        return this;
    }

    public void checkVisibleFavoritesIconForAuthorizedUser() {
       FAVORITES_ICON.shouldBe(Condition.visible);
    }

    public void checkFavoritesText() {
      Assertions.assertEquals("Избранное", FAVORITES_TEXT.getText());
    }

    public HeaderComponent checkVisibleFavoritesCounter() {
        FAVORITES_COUNTER.shouldBe(Condition.visible);
        return this;
    }

    public void checkValueFavoritesCounter(int expectedFavoritesCounter) {
        Assertions.assertEquals(expectedFavoritesCounter, Integer.parseInt(FAVORITES_COUNTER.getText()));
    }

    public void checkVisibleAccountIconForAuthorizedUser() {
        ACCOUNT_ICON.shouldBe(Condition.visible);
    }

    public HeaderComponent hoverFavoritesIconAndCheckDropdownMenuText() {
        FAVORITES_ICON.hover();
        FAVORITES_MENU_DROPDOWN.first().shouldHave(Condition.text("Мое избранное"));
        FAVORITES_MENU_DROPDOWN.last().shouldHave(Condition.text("Рекомендации менеджера"));
        return this;
    }

    public void clickDropdownMenuTextAndCheckGoToFavoritesPage() {
        FAVORITES_MENU_DROPDOWN.first().click();
        sleep(1000);
        Assertions.assertEquals(baseUrl+"/favorites", url());
    }

    public void clickDropdownMenuTextAndCheckGoToRecommendationsPage() {
        FAVORITES_MENU_DROPDOWN.last().click();
        sleep(1000);
        Assertions.assertEquals(baseUrl+"/recommendations", url());
    }


}
