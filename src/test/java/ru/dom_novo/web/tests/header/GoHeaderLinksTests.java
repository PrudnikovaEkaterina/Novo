package ru.dom_novo.web.tests.header;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dom_novo.web.pages.components.HeaderComponent;
import ru.dom_novo.web.tests.TestBase;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("FollowingHeaderLinks")
public class GoHeaderLinksTests extends TestBase {

    HeaderComponent header = new HeaderComponent();

    @BeforeEach
    void beforeEach() {
        open(baseUrl);
    }

    @Test
    @DisplayName("Переход по ссылке 'Новостройки'")
    void followingNovostroykiLink() {
        header
                .followingNovostroykiLink()
                .verifyUrlAfterFollowingNovostroykiLink();
    }

    @Test
    @DisplayName("Открытие модального окна по ссылке 'Помощь в подборе'")
    void followingHelpInChoosingLink() {
        header
                .followingApartmentQuizModalLink()
                .verifyApartmentQuizModalTitle();
    }

    @Test
    @DisplayName("Переход по ссылке 'O компании'")
    void followingAboutLink() {
        header
                .followingAboutLink()
                .verifyUrlFollowingAboutLink();
    }

    @Test
    @DisplayName("Переход по ссылке 'Контакты'")
    void followingContactsLink() {
        header
                .followingContactsLink()
                .verifyUrlFollowingContactsLink();
    }

    @Test
    @DisplayName("Переход по ссылке 'Войти'")
    void followingSingInLink() {
        header
                .checkVisibleSignInLinkForUnauthorizedUser()
                .followingSingInLink()
                .verifyUrlFollowingSingInLink();
    }

}
