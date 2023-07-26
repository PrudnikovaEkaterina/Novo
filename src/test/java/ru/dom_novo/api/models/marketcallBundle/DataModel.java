package ru.dom_novo.api.models.marketcallBundle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataModel {
    private ArrayList<BundleModel> data;
}
