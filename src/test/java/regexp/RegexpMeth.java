package regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpMeth {
    public static int extractYear(String resource) {
        int releaseYear = 0;
        Pattern p = Pattern.compile("\\d{4}");
        Matcher m = p.matcher(resource);
        while (m.find()) {
            releaseYear = Integer.parseInt(m.group());
        }
        return releaseYear;
    }

    public static List<Integer> extractYears(String source) {
        List<Integer> result = new ArrayList();
        Pattern p = Pattern.compile("\\d{4}");
        Matcher m = p.matcher(source);
        while (m.find()) {
            result.add(Integer.parseInt(m.group()));
        }
        return result;
    }

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
        for (int i = 0; i < array.length; i++) {
            Matcher m = p.matcher(array[i]);
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
        String subStr = source.codePointCount(0, source.length()) > limit ?
                source.substring(0, source.offsetByCodePoints(0, limit)) : source;
       return subStr;
    }

}
