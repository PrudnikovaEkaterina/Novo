package regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpMeth {
    public static int extractYear (String resource){
        int releaseYear=0;
        Pattern p = Pattern.compile("\\d{4}");
        Matcher m = p.matcher(resource);
        while (m.find()) {
            releaseYear = Integer.parseInt(m.group());
        }
        return releaseYear;
    }

    public static List<Integer> extractYears (String source) {
        List<Integer> result = new ArrayList();
        Pattern p = Pattern.compile("\\d{4}");
        Matcher m = p.matcher(source);
        while (m.find()) {
            result.add(Integer.parseInt(m.group()));
        }
        return result;
    }

    public static int extractPrice (String resource){
        int price =0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(resource);
        while (m.find()) {
            price = Integer.parseInt(m.group());
        }
        return price;
    }

    public static String removeSpacesFromString (String resource){
       return  resource.replaceAll(" ", "");
    }

}
