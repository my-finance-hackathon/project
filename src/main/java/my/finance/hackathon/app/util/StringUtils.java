package my.finance.hackathon.app.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public String toCapitalFirstLetter(String request) {
        if (request == null) {
            return null;
        }
        if (request.isEmpty()) {
            return "";
        }
        request = request.replaceAll(" ", "");
        return String.valueOf(request.charAt(0)).toUpperCase() + request.substring(1).toLowerCase();
    }

    public String prepareEmail(String request) {
        if (request == null || request.isEmpty()) {
            return null;
        }
        request = request.replaceAll(" ", "");
        return request.toLowerCase();
    }
}
