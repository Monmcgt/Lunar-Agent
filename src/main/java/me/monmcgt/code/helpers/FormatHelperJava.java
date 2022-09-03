package me.monmcgt.code.helpers;

public class FormatHelperJava {
    public static final String colourKey = "§";

    public static final String grey = colourKey + "7";
    public static final String pink = colourKey + "d";
    public static final String yellow = colourKey + "e";
    public static final String lime = colourKey + "a";
    public static final String dark_aqua = colourKey + "3";
    public static final String red = colourKey + "c";

    public static String formatStats(String stars, String name, String fkdr, String winrate, String winstreak) {
        boolean nicked = false;
        String starColour = "";
        if (stars == null) {
            stars = "???";
            nicked = true;
            starColour = dark_aqua;
        } else if (Integer.parseInt(stars) <= 200) {
            starColour = dark_aqua;
        } else {
            starColour = red;
        }

        if (nicked) {
            return String.format("%s[%sLA%s] %s[%s] %s%s %s%s", grey, pink, grey, yellow, stars, dark_aqua, name, yellow, "is nicked!");
        } else {
            return String.format("%s[%sLA%s] %s[%s] %s%s %s- %s%s %sFKDR - %s%s %sWLR - %s%s %sWS", grey, pink, grey, yellow, stars, starColour, name, yellow, lime, fkdr, yellow, lime, winrate, yellow, lime, winstreak, yellow);
        }
    }

    public static String addPrefix(String message) {
        return String.format("%s[%sLA%s] %s", grey, pink, grey, message);
    }
}
