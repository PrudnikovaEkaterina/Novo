package ru.prudnikova.web.tests.cardNovostroyki;

import com.codeborne.pdftest.PDF;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.api.models.buildingDto.DocumentDto;
import ru.prudnikova.api.steps.cardNovostroykiApiSteps.CardNovostroykiDocumentsApiSteps;
import ru.prudnikova.testData.GenerationData;
import ru.prudnikova.web.pages.CardNovostroykiPage;
import ru.prudnikova.web.tests.TestBase;

import java.io.File;
import java.io.IOException;
import java.util.List;
import static com.codeborne.selenide.Selenide.*;


@Tag("Web")
@Owner("PrudnikovaEkaterina")
@TmsLink("https://tracker.yandex.ru/NOVODEV-575")
public class CardNovostroykiDocumentsTests extends TestBase {
    CardNovostroykiPage cardNovostroykiPage =new CardNovostroykiPage();
    int buildingId=GenerationData.setRandomBuildingId();
    List<DocumentDto> documentList = CardNovostroykiDocumentsApiSteps.getDocumentsList(buildingId);
    int documentListSize = CardNovostroykiDocumentsApiSteps.getDocumentsListSize(documentList);
    List<String> documentTitleListApi = CardNovostroykiDocumentsApiSteps.getDocumentsTitleList(documentList);

    @Test
    @DisplayName("Проверить отображение блока Документация в карточке ЖК")
    void checkCardSectionDocumentTitle () {
        cardNovostroykiPage
                .openCard(buildingId)
                .checkCardSectionDocumentTitle();
    }

    @Test
    @DisplayName("Проверить, что количество документов в блоке Документация соответсвует данным из апи")
    void checkCardDocumentsSize () {
        cardNovostroykiPage
                .openCard(buildingId)
                .hoverCardSectionDocumentTitle();
        while (cardNovostroykiPage.checkVisibleCardDocumentsShowMoreButton()) {
           cardNovostroykiPage
                   .clickCardDocumentsShowMoreButton()
                   .hoverCardSectionDocumentTitle();}
        cardNovostroykiPage.checkCardDocumentsListSize(documentListSize);
    }

    @Test
    @DisplayName("Проверить, что title каждого из документов в блоке Документация соответсвует данным из апи")
    void checkCardDocumentsTitles () {
        cardNovostroykiPage
                .openCard(buildingId)
                .hoverCardSectionDocumentTitle();
        while (cardNovostroykiPage.checkVisibleCardDocumentsShowMoreButton()) {
            cardNovostroykiPage
                    .clickCardDocumentsShowMoreButton()
                    .hoverCardSectionDocumentTitle();}
        List<String> titleListWeb= cardNovostroykiPage.getCardDocumentsTitle();
        assert documentTitleListApi.containsAll(titleListWeb);
    }

    @Test
    @DisplayName("Проверить загрузку и чтение файла формата .pdf по ссылке в блоке Документация")
    void checkDownloadPdfFile () throws IOException {
        cardNovostroykiPage.openCard(15054);
        String expectedText="Прокшино";
        File download = $("a[href*='https://move.ru/novostroyka/admin_document/16271402/']").download();
        PDF pdf = new PDF(download);
        Assertions.assertTrue(pdf.text.contains(expectedText));
    }

}

