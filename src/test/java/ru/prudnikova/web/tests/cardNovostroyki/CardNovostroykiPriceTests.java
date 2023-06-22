package ru.prudnikova.web.tests.cardNovostroyki;

import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import regexp.RegexpMeth;
import ru.prudnikova.api.steps.cardNovostroykiApiSteps.CardNovostroykiApi;
import ru.prudnikova.dataBase.managers.BuildingDAO;
import ru.prudnikova.testData.GenerationData;
import ru.prudnikova.web.pages.CardNovostroykiPage;
import ru.prudnikova.web.tests.TestBase;

import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Tag("Web")
@Tag("Api")
@Owner("PrudnikovaEkaterina")
public class CardNovostroykiPriceTests extends TestBase {
    CardNovostroykiPage cardNovostroykiPage = new CardNovostroykiPage();

    @Test
    @DisplayName("Проверить значение цен в карточке ЖК по объектам без предложений от ТА")
    @TmsLink("https://tracker.yandex.ru/NOVODEV-558")
    void checkPriceValueForCardNovostroykiWithoutFlatsFromTrendAgent() throws IOException {
        List<Integer> buildingIdList = BuildingDAO.selectBuildingIdWithoutFlatsWherePricesSlugContainsRoom();
        int buildingId= GenerationData.setRandomBuildingId(buildingIdList);
        cardNovostroykiPage.openZhkPage(buildingId);
        int cardNovostroykiPriceValue = cardNovostroykiPage.getPriceValue();
        int cardNovostroykiProfilePriceValue = cardNovostroykiPage.getProfilePriceValue();
        int priceMin = CardNovostroykiApi.selectPriceMin(buildingId);
        Assertions.assertEquals(priceMin, cardNovostroykiPriceValue);
        Assertions.assertEquals(priceMin, cardNovostroykiProfilePriceValue);

    }

}
