package ru.prudnikova.web.tests.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.steps.meApiSteps.MeApiSteps;
import ru.prudnikova.web.pages.MainPage;
import ru.prudnikova.web.pages.components.HeaderComponent;
import ru.prudnikova.web.tests.TestBase;

public class HeaderAccountTests extends TestBase {
    MainPage mainPage = new MainPage();
    HeaderComponent headerComponent =new HeaderComponent();
    String phoneNumber = "79085040794";

    @Test
    @DisplayName("Отображение в header иконки Профиля для авторизованного пользователя")
    void checkVisibleAccountIconForAuthorizedUser () {
        mainPage.openMainPageWithApiAuth(phoneNumber);
        headerComponent.checkVisibleAccountIconForAuthorizedUser();
    }

    @Test
    @DisplayName("Отображение в header имени пользователя при его наличии")
    void checkVisibleAccountNameForAuthorizedUser () {
        String name = MeApiSteps.getUserName(phoneNumber);
        if (name!=null){
            mainPage.openMainPageWithApiAuth(phoneNumber);
            headerComponent.checkAccountName(name);
        }
    }

}
