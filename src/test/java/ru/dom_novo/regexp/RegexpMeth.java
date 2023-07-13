package ru.dom_novo.regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpMeth {

    public static int extractPrice(String resource) {
        int price = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(resource);
        if(m.find()) {
            price = Integer.parseInt(m.group());
        }
        return price;
    }

    public static String removeSpacesFromString(String resource) {
        return resource.replaceAll(" ", "");
    }

    public static List<Double> extractAreaList(String source) {
        List<Double> squareList = new ArrayList<>();
        String[] array = source.split("-");
        Pattern p = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        for (String s : array) {
            Matcher m = p.matcher(s);
            if (m.find()) {
                squareList.add(Double.parseDouble(m.group()));
            }
        }
        return squareList;
    }

    public static double extractPriceOrAreaDouble (String source) {
        double price =0;
        Pattern p = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher m = p.matcher(source);
        if (m.find()) {
            price = Double.parseDouble(m.group());
        }
        return price;
    }

    public static String substring (int limit, String source){
        return source.codePointCount(0, source.length()) > limit ?
                source.substring(0, source.offsetByCodePoints(0, limit)) : source;
    }

    public static String getAllNumbersFromString (String source){
       return source.replaceAll("[^0-9]", "");
    }

}
