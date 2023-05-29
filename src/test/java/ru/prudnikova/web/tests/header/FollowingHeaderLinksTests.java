package ru.prudnikova.web.tests.header;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.web.pages.components.Header;
import ru.prudnikova.web.tests.TestBase;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

@Tag("Web")
@Story("FollowingHeaderLinks")
public class FollowingHeaderLinksTests extends TestBase {

    Header header = new Header();

    @BeforeEach
    void beforeEach() {
        open(baseUrl);
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Переход по ссылке 'Новостройки'")
    void followingNovostroykiLink() {
        header.followingNovostroykiLink().verifyUrlAfterFollowingNovostroykiLink();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Открытие модального окна по ссылке 'Помощь в подборе'")
    void followingHelpInChoosingLink() {
        header.followingApartmentQuizModalLink().verifyApartmentQuizModalTitle();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Переход по ссылке 'O компании'")
    void followingAboutLink() {
        header.followingAboutLink().verifyUrlFollowingAboutLink();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Переход по ссылке 'Контакты'")
    void followingContactsLink() {
        header.followingContactsLink().verifyUrlFollowingContactsLink();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Переход по ссылке 'Войти'")
    void followingSingInLink() {
        header.followingSingInLink().verifyUrlFollowingSingInLink();
    }

}
