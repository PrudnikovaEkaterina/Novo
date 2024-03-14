package ru.dom_novo.web.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
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
            FAVORITES_ICON = $x("//*[@href='#favorite-2-sprite']"),
            FAVORITES_TEXT = $(".favorites-menu-dropdown__favorites-text"),
            FAVORITES_COUNTER = $(".favorites-menu-dropdown__favorites-counter"),
            ACCOUNT_ICON = $x("//*[@href='#account-2-sprite']");

    private final ElementsCollection
            FAVORITES_MENU_DROPDOWN = $$(".favorites-menu-dropdown__dropdown-item");

    @Step("Follow the link 'Novostroyki' in the header menu")
    public HeaderComponent followingNovostroykiLink() {
        NOVOSTROYKI_LINK.click();
        sleep(2000);
        return this;
    }

    @Step("Check if the link is correct")
    public void verifyUrlAfterFollowingNovostroykiLink() {
        Assertions.assertEquals(baseUrl + "/novostroyki", url());
    }

    @Step("Follow the link 'Assistance in selection' in the header menu")
    public ApartmentQuizModalComponent followingApartmentQuizModalLink() {
        APARTMENT_QUIZ_MODAL_LINK.click();
        sleep(2000);
        return apartmentQuizModal;
    }

    @Step("Follow the link 'About the company' in the header menu")
    public HeaderComponent followingAboutLink() {
        ABOUT_LINK.click();
        sleep(1000);
        return this;
    }

    @Step("Check if the link is correct")
    public void verifyUrlFollowingAboutLink() {
        Assertions.assertEquals(baseUrl + "/about", url());
    }

    @Step("Follow the link 'Contacts' in the header menu")
    public HeaderComponent followingContactsLink() {
        CONTACTS_LINK.click();
        sleep(1000);
        return this;
    }

    @Step("Check if the link is correct")
    public void verifyUrlFollowingContactsLink() {
        Assertions.assertEquals(baseUrl + "/contacts", url());
    }

    @Step("Follow the link 'Login' in the header menu")
    public HeaderComponent followingLoginLink() {
        SIGN_IN_LINK.click();
        sleep(1000);
        return this;
    }

    @Step("Check if the link is correct")
    public void verifyUrlFollowingLoginLink() {
        Assertions.assertEquals(baseUrl + "/auth", url());
    }

    @Step("Hover over the Profile icon and check that there is a Profile menu item in the drop-down list")
    public void hoverHeaderAccountIconAndCheckUserMenuDropdownText() {
        HEADER_ACCOUNT_TEXT.hover();
        USER_MENU_DROPDOWN_TEXT_PROFILE.shouldHave(Condition.text("Профиль"));

    }

    @Step("Check username display in the header menu")
    public void checkAccountName(String userName) {
        HEADER_ACCOUNT_TEXT.shouldHave(Condition.text(userName));
    }

    @Step("Logout")
    public void logout() {
        HEADER_ACCOUNT_TEXT.hover();
        USER_MENU_DROPDOWN_TEXT_EXIT.click();
        HEADER_ACCOUNT_TEXT.shouldHave(Condition.text("Войти"));
    }

    @Step("Click header logo")
    public HeaderComponent clickHeaderLogo() {
        HEADER_LOGO.click();
        sleep(500);
        return this;
    }

    @Step("Click header logo")
    public void checkGoToMainPageAfterClickHeaderLogo() {
        Assertions.assertEquals(baseUrl + "/", url());
    }

    @Step("Check the visibility of the Login link for an unauthorized user")
    public HeaderComponent checkVisibleSignInLinkForUnauthorizedUser() {
        SIGN_IN_LINK.shouldBe(Condition.visible);
        return this;
    }

    @Step("Check the visibility of the Favorites icon for an authorized user")
    public void checkVisibleFavoritesIconForAuthorizedUser() {
        FAVORITES_ICON.shouldBe(Condition.visible);
    }

    @Step("Check the visibility of the Favorites text for an authorized user")
    public void checkFavoritesText() {
        Assertions.assertEquals("Избранное", FAVORITES_TEXT.getText());
    }

    @Step("Check the visibility of the Favorites counter for an authorized user")
    public HeaderComponent checkVisibleFavoritesCounter() {
        FAVORITES_COUNTER.shouldBe(Condition.visible);
        return this;
    }

    @Step("Check the value of the Favorites counter")
    public void checkValueFavoritesCounter(int expectedFavoritesCounter) {
        Assertions.assertEquals(expectedFavoritesCounter, Integer.parseInt(FAVORITES_COUNTER.getText()));
    }

    @Step("Check the visibility of the Account icon")
    public void checkVisibleAccountIconForAuthorizedUser() {
        ACCOUNT_ICON.shouldBe(Condition.visible);
    }

    @Step("Hover over the Favorites icon and check the link text in the drop-down list")
    public HeaderComponent hoverFavoritesIconAndCheckDropdownMenuText() {
        FAVORITES_ICON.hover();
        FAVORITES_MENU_DROPDOWN.first().shouldHave(Condition.text("Мое избранное"));
        FAVORITES_MENU_DROPDOWN.last().shouldHave(Condition.text("Рекомендации менеджера"));
        return this;
    }

    @Step("Click on the My favorites link in the drop-down list and check that the transition is correct")
    public void clickDropdownMenuTextAndCheckGoToFavoritesPage() {
        FAVORITES_MENU_DROPDOWN.first().click();
        sleep(1000);
        Assertions.assertEquals(baseUrl + "/favorites", url());
    }

    @Step("Click on the Manager's recommendations link in the drop-down list and check that the transition is correct")
    public void clickDropdownMenuTextAndCheckGoToRecommendationsPage() {
        FAVORITES_MENU_DROPDOWN.last().click();
        sleep(1000);
        Assertions.assertEquals(baseUrl + "/recommendations", url());
    }

}
