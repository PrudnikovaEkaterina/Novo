package ru.prudnikova.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.prudnikova.dataBase.managers.CallbackPhonesManager;
import ru.prudnikova.testData.GenerationData;
import ru.prudnikova.dataBase.domain.CallbackPhonesBD;
import ru.prudnikova.web.pages.NovostroykiPage;
import ru.prudnikova.web.tests.TestBase;

import java.util.List;

import static com.codeborne.selenide.Configuration.baseUrl;

@Tag("Web")
@Story("CallbackPhone")
public class CallMeTests extends TestBase {
    NovostroykiPage novostroykiPage = new NovostroykiPage();
    CallbackPhonesManager callbackPhonesManager = new CallbackPhonesManager();

    @BeforeEach
    void beforeEach() {
        novostroykiPage.openNovostroykiPage();
    }

    @Test
    @Owner("PrudnikovaEkaterina")
    @DisplayName("Отправить заполненную форму Заказать звонок и проверить добавление соответсвующей записи в БД")
    void orderCallBackAndСheckAdditionInDatabase() {
        String callbackPhoneModalTitle = "Укажите Ваш номер телефона и мы перезвоним!";
        String phoneNumber = GenerationData.setRandomPhoneNumber();
        String phoneThanksModalTitle = "Спасибо!\n" + "Мы уже обрабатываем вашу заявку";
        novostroykiPage.hoverSearchItemContent().openCallMeWidget().verifyCallbackPhoneModalTitle(callbackPhoneModalTitle)
                .setPhoneNumber(phoneNumber).clickCallMeButton().verifyPhoneThanksModalTitle(phoneThanksModalTitle);
        List<CallbackPhonesBD> result = callbackPhonesManager.selectLastEntryFromCallbackPhonesTables();
        Assertions.assertEquals(phoneNumber, result.get(0).getPhone());
        Assertions.assertEquals(baseUrl + "/novostroyki", result.get(0).getLink());
    }

}
