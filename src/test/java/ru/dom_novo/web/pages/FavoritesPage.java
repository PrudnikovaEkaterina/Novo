package ru.dom_novo.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import ru.dom_novo.api.steps.authApiSteps.AuthApi;
import ru.dom_novo.regexp.RegexpMeth;


import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;


public class FavoritesPage {
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
            SEARCH_ITEM_CLICK_AREA = $$(".search-item__click-area");

    public FavoritesPage openMePageWithApiAuth(String phoneNumber) {
        AuthApi.setAuthCookiesToBrowser(phoneNumber);
        open(baseUrl + "/favorites");
        return this;
    }

    public void checkFavoritesHeaderTitle() {
        FAVORITES_HEADER_TITLE.shouldBe(Condition.visible);
    }

    public void checkFavoritesManagerBlock() {
        FAVORITES_MANAGER.shouldBe(Condition.visible);
        Assertions.assertEquals("Ваш персональный менеджер", FAVORITES_MANAGER_ROLE.getText());
    }

    public String getFavoritesManagerName() {
        return FAVORITES_MANAGER_NAME.getText();
    }

    public String getFavoritesManagerPhone() {
        return RegexpMeth.getAllNumbersFromString(FAVORITES_MANAGER_PHONE.getText());
    }

    public FavoritesPage clickFavoritesManagerChatText() {
        FAVORITES_MANAGER_CHAT_TEXT.click();
        return this;
    }

    public void checkUrlAfterClickFavoritesManagerChatText(String managerPhoneExpected) {
        switchTo().window(1);
        String url = url();
        Assertions.assertTrue(url.contains(managerPhoneExpected));
    }

    public void checkFavoritesHeaderMenu() {
        FAVORITES_HEADER_MENU.first().shouldHave(Condition.text("Жилые комплексы"));
        FAVORITES_HEADER_MENU.get(1).shouldHave(Condition.text("Квартиры"));
        FAVORITES_HEADER_MENU.last().shouldHave(Condition.text("Рекомендации менеджера"));
    }

    public int getFavoritesBuildingsCount() {
        return Integer.parseInt(FAVORITES_NAV_NAV_COUNTER.first().getText());
    }

    public int getFavoritesFlatsCount() {
        return Integer.parseInt(FAVORITES_NAV_NAV_COUNTER.get(1).getText());
    }

    public int getRecommendationsCount() {
        return Integer.parseInt(FAVORITES_NAV_NAV_COUNTER.last().getText());
    }

    public void setSortFavoritesBuildings(String sort) {
        if (getFavoritesBuildingsCount() != 0) {
            FAVORITES_SORT_CURRENT.click();
            DROPDOWN_MENU_ITEM.findBy(Condition.text(sort)).click();
        }
    }

    public List<String> getBuildingsTitleEng() {
        String delete = "https://novo-dom.ru/";
        List<String> list = new ArrayList<>();
        if (getFavoritesBuildingsCount() != 0) {
            for (SelenideElement selenideElement : SEARCH_ITEM_CLICK_AREA) {
                list.add(selenideElement.getAttribute("href").replace (delete, ""));
            }
        }
        return list;
    }

}
