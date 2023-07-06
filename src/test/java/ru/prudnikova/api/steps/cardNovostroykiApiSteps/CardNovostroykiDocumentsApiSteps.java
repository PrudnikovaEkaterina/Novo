package ru.prudnikova.api.steps.cardNovostroykiApiSteps;

import io.qameta.allure.Step;
import ru.prudnikova.api.models.buildingDto.DocumentDto;
import ru.prudnikova.api.models.buildingDto.RootDto;

import java.util.ArrayList;
import java.util.List;


public class CardNovostroykiDocumentsApiSteps {
    @Step("Получить список документов ЖК")
    public static List<DocumentDto> getDocumentsList(int buildingId) {
        RootDto root = CardNovostroykiApiSteps.getBuildingData(buildingId);
        List<DocumentDto> documentList = root.getData().getDocuments();
        assert (documentList!=null);
        return documentList;
    }

    @Step("Получить размер списка документов ЖК")
    public static int getDocumentsListSize (List<DocumentDto> documentsList) {
       return documentsList.size();
    }

    @Step("Получить список title документов ЖК")
    public static List<String> getDocumentsTitleList(List<DocumentDto> documentsList) {
        List<String> titleList=new ArrayList<>();
        for (int i=0; i<documentsList.size(); i++){
           titleList.add(documentsList.get(i).getTitle());
        }
        return titleList;
    }
}
