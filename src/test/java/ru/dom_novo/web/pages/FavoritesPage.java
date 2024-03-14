package ru.dom_novo.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import ru.dom_novo.api.steps.authApiSteps.AuthApiSteps;
import ru.dom_novo.web.pages.components.CallMeWidgetComponent;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static java.lang.Thread.sleep;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;


public class FavoritesPage {

    CallMeWidgetComponent callMeWidgetComponent = new CallMeWidgetComponent();
    private final SelenideElement
            FAVORITES_HEADER_TITLE = $(".favorites-header__title"),
            FAVORITES_MANAGER = $(".favorites-manager"),
            FAVORITES_MANAGER_ROLE = $(".favorites-manager__role"),
            FAVORITES_MANAGER_NAME = $(".favorites-manager__name"),
            FAVORITES_MANAGER_PHONE = $(".favorites-manager__phone"),
            FAVORITES_MANAGER_CHAT_TEXT = $(".favorites-manager__chat-text"),
            FAVORITES_SORT_CURRENT = $(".favorites-sort__current");


    private final ElementsCollection
            FAVORITES_HEADER_MENU = $$(".favorites-nav__nav-text"),
            FAVORITES_NAV_NAV_COUNTER = $$(".favorites-nav__nav-counter"),
            DROPDOWN_MENU_ITEM = $$(".el-dropdown-menu__item"),
            SEARCH_ITEM_CLICK_AREA = $$(".search-item__click-area"),
            FAVORITES_NOVOSTROYKI_ITEMS_DATE_TEXT = $$(".favorites-novostroyki-items__date-text"),
            FAVORITE_BUTTON_ICON = $$(".favorite-button__icon"),
            CALL_ME_WIDGET_BUTTON = $$(".call-me-widget__button");

    @Step("Open the Favorites page with pre-installed authorization cookies")
    public FavoritesPage openFavoritesPageWithAuthUsePhoneNumber(String phoneNumber) {
        AuthApiSteps.setAuthCookiesToBrowserWithPhoneNumber(phoneNumber);
        open(baseUrl + "/favorites");
        return this;
    }

    @Step("Open the Favorites page with pre-installed authorization cookies")
    public FavoritesPage openFavoritesPageWithAuthUseLoginResponse(Response loginResponse) {
        AuthApiSteps.setAuthCookiesToBrowserWithLoginResponse(loginResponse);
        open(baseUrl + "/favorites");
        return this;
    }

    @Step("Check the visibility of the Favorites header")
    public FavoritesPage checkFavoritesHeaderTitle() {
        FAVORITES_HEADER_TITLE.shouldBe(Condition.visible);
        return this;
    }

    @Step("Check the visibility of the Your Personal Manager")
    public void checkFavoritesManagerBlock() {
        FAVORITES_MANAGER.shouldBe(Condition.visible);
        Assertions.assertEquals("Ваш персональный менеджер", FAVORITES_MANAGER_ROLE.getText());
    }

    @Step("Get the name of your personal manager")
    public String getFavoritesManagerName() {
        return FAVORITES_MANAGER_NAME.getText();
    }

    @Step("Get the phone of your personal manager")
    public String getFavoritesManagerPhone() {
        return FAVORITES_MANAGER_PHONE.getText();
    }

    @Step("Click 'Write to WA' in the block with a personal manager")
    public void clickFavoritesManagerChatText() {
        FAVORITES_MANAGER_CHAT_TEXT.click();
    }

    @Step("Check that the link contains the correct manager’s phone number")
    public void checkUrlAfterClickFavoritesManagerChatText(String managerPhoneExpected) {
        switchTo().window(1);
        String url = url();
        Assertions.assertTrue(url.contains(managerPhoneExpected));
    }

    @Step("Check link names in favorites header menu")
    public void checkFavoritesHeaderMenu() {
        FAVORITES_HEADER_MENU.first().shouldHave(Condition.text("Жилые комплексы"));
        FAVORITES_HEADER_MENU.get(1).shouldHave(Condition.text("Квартиры"));
        FAVORITES_HEADER_MENU.last().shouldHave(Condition.text("Рекомендации менеджера"));
    }

    @Step("Get count of featured buildings")
    public int getFavoritesBuildingsCount() {
        return Integer.parseInt(FAVORITES_NAV_NAV_COUNTER.first().getText());
    }

    @Step("Get count of featured flats")
    public int getFavoritesFlatsCount() {
        return Integer.parseInt(FAVORITES_NAV_NAV_COUNTER.get(1).getText());
    }

    @Step("Get count of manager recommendations")
    public int getRecommendationsCount() {
        return Integer.parseInt(FAVORITES_NAV_NAV_COUNTER.last().getText());
    }

    @Step("Set sorting {sort}")
    public FavoritesPage setSortFavoritesBuildings(String sort) throws InterruptedException {
        sleep(2000);
        FAVORITES_SORT_CURRENT.click();
        DROPDOWN_MENU_ITEM.findBy(Condition.text(sort)).click();
        sleep(2000);
        return this;
    }

    @Step("Collect buildings title-eng")
    public List<String> collectBuildingsTitleEng() {
        String delete = baseUrl + "/";
        Selenide.sleep(3000);
        return SEARCH_ITEM_CLICK_AREA
                .asFixedIterable()
                .stream()
                .map(el -> requireNonNull(el.getAttribute("href"))
                        .replace(delete, ""))
                .collect(toList());
    }

    @Step("Collect buildings dates when were added to favorites")
    public List<String> getBuildingsDateText() {
        String delete = "Добавлено ";
        Selenide.sleep(2000);
        return FAVORITES_NOVOSTROYKI_ITEMS_DATE_TEXT
                .asFixedIterable()
                .stream()
                .map(e -> e.getText().replace(delete, ""))
                .collect(toList());
    }

    @Step("Click on the Favorites icon for the first buildings on the page")
    public void clickFavoriteIconForFirstSearchBuilding() {
        FAVORITE_BUTTON_ICON.first().click();
        Selenide.sleep(1000);
    }

    @Step("Hover to the first buildings on the page")
    public FavoritesPage hoverFirstSearchItem() {
        SEARCH_ITEM_CLICK_AREA.first().hover();
        return this;
    }

    @Step("Click on the Call me button")
    public FavoritesPage clickFirstCallMeWidgetButton() {
        CALL_ME_WIDGET_BUTTON.first().click();
        return this;
    }

    @Step("Check title in callback modal window")
    public void checkCallbackPhoneModalTitle() {
        callMeWidgetComponent.verifyCallbackPhoneModalTitle("Укажите Ваш номер телефона и мы перезвоним!");
    }

}
