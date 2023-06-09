package ru.dom_novo.web.tests.novostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.dom_novo.dataBase.dao.CallbackPhonesDao;
import ru.dom_novo.testData.GenerationData;
import ru.dom_novo.dataBase.entities.CallbackPhonesEntity;
import ru.dom_novo.web.pages.NovostroykiPage;
import ru.dom_novo.web.tests.TestBase;

import java.util.List;

import static com.codeborne.selenide.Configuration.baseUrl;

@Tag("Web")
@Owner("PrudnikovaEkaterina")
@Story("CallbackPhone")
public class CallMeTests extends TestBase {
    NovostroykiPage novostroykiPage = new NovostroykiPage();
    CallbackPhonesDao callbackPhonesManager = new CallbackPhonesDao();

    @BeforeEach
    void beforeEach() {
        novostroykiPage.openNovostroykiPage();
    }

    @Test
    @DisplayName("Отправить заполненную форму Заказать звонок и проверить добавление соответсвующей записи в БД")
    void orderCallBackAndCheckAdditionInDatabase() {
        String callbackPhoneModalTitle = "Укажите Ваш номер телефона и мы перезвоним!";
        String phoneNumber = GenerationData.setRandomPhoneNumber();
        String phoneThanksModalTitle = "Спасибо!\n" + "Мы уже обрабатываем вашу заявку";
        novostroykiPage
                .hoverSearchItemContent()
                .openCallMeWidget()
                .verifyCallbackPhoneModalTitle(callbackPhoneModalTitle)
                .setPhoneNumber(phoneNumber)
                .clickCallMeButton()
                .verifyPhoneThanksModalTitle(phoneThanksModalTitle);
        List<CallbackPhonesEntity> result = callbackPhonesManager.selectLastEntryFromCallbackPhonesTables();
        Assertions.assertEquals(phoneNumber, result.get(0).getPhone());
        Assertions.assertEquals(baseUrl + "/novostroyki", result.get(0).getLink());
    }

}
