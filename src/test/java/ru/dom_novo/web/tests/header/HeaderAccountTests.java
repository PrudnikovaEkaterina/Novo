package ru.dom_novo.web.tests.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dom_novo.api.steps.meApiSteps.MeApiSteps;
import ru.dom_novo.web.pages.MainPage;
import ru.dom_novo.web.pages.components.HeaderComponent;
import ru.dom_novo.web.tests.TestBase;

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
