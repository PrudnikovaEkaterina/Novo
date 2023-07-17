package ru.dom_novo.api.steps.cardNovostroykiApiSteps;

import io.qameta.allure.Step;
import ru.dom_novo.api.models.buildingModels.DocumentModel;
import ru.dom_novo.api.models.buildingModels.RootModel;

import java.util.ArrayList;
import java.util.List;


public class CardNovostroykiDocumentsApiSteps {
    @Step("Получить список документов ЖК")
    public static List<DocumentModel> getDocumentsList(int buildingId) {
        RootModel root = CardNovostroykiApiSteps.getBuildingData(buildingId);
        List<DocumentModel> documentList = root.getData().getDocuments();
        assert (documentList!=null);
        return documentList;
    }

    @Step("Получить размер списка документов ЖК")
    public static int getDocumentsListSize (List<DocumentModel> documentsList) {
       return documentsList.size();
    }

    @Step("Получить список title документов ЖК")
    public static List<String> getDocumentsTitleList(List<DocumentModel> documentsList) {
        List<String> titleList=new ArrayList<>();
        for (int i=0; i<documentsList.size(); i++){
           titleList.add(documentsList.get(i).getTitle());
        }
        return titleList;
    }
}
