package ru.prudnikova.web.tests.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.steps.meApiSteps.MeApiSteps;
import ru.prudnikova.web.pages.MainPage;
import ru.prudnikova.web.pages.components.HeaderComponent;
import ru.prudnikova.web.tests.TestBase;

public class HeaderFavoriteTests extends TestBase {
    MainPage mainPage = new MainPage();
    HeaderComponent headerComponent =new HeaderComponent();

    @Test
    @DisplayName("Отображение в header иконки Избранное для авторизованного пользователя")
    void checkVisibleFavoritesIconForAuthorizedUser () {
        String phoneNumber = "72342123122";
        mainPage.openMainPageWithApiAuth(phoneNumber);
        headerComponent.checkVisibleFavoritesIconForAuthorizedUser();
    }

    @Test
    @DisplayName("Отображение в header текста Избранное для авторизованного пользователя")
    void checkVisibleFavoritesTextForAuthorizedUser () {
        String phoneNumber = "79295690868";
        mainPage.openMainPageWithApiAuth(phoneNumber);
        headerComponent.checkFavoritesText();
    }

    @Test
    @DisplayName("Проверка отображения в header счетчика Избранного и его значения для авторизованного пользователя")
    void checkVisibleFavoritesCounterForAuthorizedUser () {
        String phoneNumber = "79085040794";
        int favoritesCounter = MeApiSteps.getFavoritesCounterForAuthUser(phoneNumber);
        if (favoritesCounter!=0){
            mainPage.openMainPageWithApiAuth(phoneNumber);
            headerComponent
                    .checkVisibleFavoritesCounter()
                    .checkValueFavoritesCounter(favoritesCounter);
        }
    }

    @Test
    @DisplayName("Проверка появления выпадающего меню при ховере на текст Избранное в header")
    void hoverFavoritesTextAndCheckDropdownMenuText () {
        String phoneNumber = "79885470530";
        mainPage.openMainPageWithApiAuth(phoneNumber);
        headerComponent.hoverFavoritesIconAndCheckDropdownMenuText();
    }

    @Test
    @DisplayName("Проверка перехода на страницу Мое избранное при клике на соответствующий пункт выпадающего меню в header")
    void checkGoToFavoritesPage () {
        String phoneNumber = "79885470530";
        mainPage.openMainPageWithApiAuth(phoneNumber);
        headerComponent
                .hoverFavoritesIconAndCheckDropdownMenuText()
                .clickDropdownMenuTextAndCheckGoToFavoritesPage();
    }

    @Test
    @DisplayName("Проверка перехода на страницу Рекомендации менеджера при клике на соответствующий пункт выпадающего меню в header")
    void checkGoToRecommendationsPage () {
        String phoneNumber = "77000980987";
        mainPage.openMainPageWithApiAuth(phoneNumber);
        headerComponent
                .hoverFavoritesIconAndCheckDropdownMenuText()
                .clickDropdownMenuTextAndCheckGoToRecommendationsPage();
    }



}
