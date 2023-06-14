package ru.prudnikova.web.tests.me;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.prudnikova.testData.GenerationData;
import ru.prudnikova.web.pages.MePage;
import ru.prudnikova.web.pages.components.HeaderComponent;
import ru.prudnikova.web.tests.TestBase;

import static java.lang.Thread.sleep;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("UserProfile")
public class MeTests extends TestBase {

    MePage mePage = new MePage();
    HeaderComponent header = new HeaderComponent();
    String phoneNumber = "79281960486";

    @BeforeEach
    void beforeEach() {
        mePage.openMePageWithApiAuth(phoneNumber);
    }

    @Test
    @DisplayName("Изменение имени пользователя в профиле")
    void changeUserName() throws InterruptedException {
        String userName = GenerationData.setRandomUserName();
        mePage.changeUserName(userName);
        header.checkAccountName(userName);
        sleep(1000);
    }

    @Test
    @DisplayName("Изменение email пользователя в профиле")
    void changeUserEmail() {
        String userEmail = GenerationData.setRandomEmail();
        mePage
                .changeUserEmail(userEmail)
                .verifyChangeUserEmail(userEmail);
    }

    @AfterEach
    void afterEach() {
        header.logout();
    }

}
