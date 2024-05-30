package infra.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static infra.reporting.MultiReporter.info;

public class RegexWrapper {


    public static String getFirstGroup(String content, String regex) {
        return getRegexByGroup(content, regex, 1);
    }

    public static String getRegexByGroup(String content, String regex, int group) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        if (m.find()) {
            return m.group(group);
        } else {
            return null; // not found
        }
    }

    public static boolean isStringMatch(String value, String match) {
        return value.matches(".*\\b" + match + "\\b.*");
    }

    public static String getFirstGroupOrDefault(String content, String regex, String defaultValue) {
        String result = getFirstGroup(content, regex);
        return result != null ? result : defaultValue;
    }
    public static String getFirstGroupOrDefault(String content, String[] regexOptions, String defaultValue) {
        for (String currentRegex: regexOptions) {
            String result = getFirstGroup(content, currentRegex);
            if (result != null) {
                return result;
            }
        }

        return defaultValue;
    }

    public static int stringToInteger(String input) {
        info("Converting string of " + input + " to int");
        String onlyNumeric = input.replaceAll("[^0-9.-]", "");
        return (int)Double.parseDouble(onlyNumeric);
    }

    public static double stringToDouble(String input) {
        info("Converting string of " + input + " to double");
        String onlyNumeric = input.replaceAll("[^0-9.-]", "");
        return Double.parseDouble(onlyNumeric);
    }

    public static float stringToFloat(String input) {
        info("Converting string of " + input + " to int");
        String onlyNumeric = input.replaceAll("[^0-9.-]", "");
        return Float.parseFloat(onlyNumeric);
    }

    public static boolean isValidWalletAddress(String input) {
        return isValidValueByRegex(input, "^0x[a-fA-F0-9]{40}$");
    }

    public static boolean isValidValueByRegex(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.find();
    }

    public static String getTestNameFromClassName(String className) {
        return getFirstGroup(className, "(AUT[\\d]+)_");
    }

    public static String getDomainNameFromClassName(String className) {
        String result = getFirstGroup(className, "AUT[\\d]+_([A-Za-z]{2,9})_");
        if (result != null) {
            return result;
        }

        return getFirstGroup(className, "cicd\\.([A-Z]{2,3})\\.");
    }

    public static String blankIfNull(String s) {
        return s == null ? "" : s;
    }

    public static String trimUSCountry(String s) { return s.equals("United States of America") ? "United States" : s; }

    public static String replaceWhiteSpaceForCharacter (String s, String ch) { return s.replaceAll(" ", ch); }

}
