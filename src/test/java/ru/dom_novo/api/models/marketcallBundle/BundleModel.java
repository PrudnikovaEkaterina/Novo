package ru.dom_novo.api.models.marketcallBundle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BundleModel {
    private Integer id;
    private String title;
    private String state;
    private ArrayList<OfferModel> offers;
}
