package infra.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static infra.reporting.MultiReporter.info;

public class RegexWrapper {


    // Retrieves the first matching group from the content based on the provided regex
    public static String getFirstGroup(String content, String regex) {
        return getRegexByGroup(content, regex, 1);
    }

    // Retrieves the specified group from the content based on the provided regex
    public static String getRegexByGroup(String content, String regex, int group) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        if (m.find()) {
            return m.group(group);
        } else {
            return null; // not found
        }
    }

    // Checks if the value matches the specified string
    public static boolean isStringMatch(String value, String match) {
        return value.matches(".*\\b" + match + "\\b.*");
    }

    // Retrieves the first matching group from the content based on the provided regex or returns the default value if not found
    public static String getFirstGroupOrDefault(String content, String regex, String defaultValue) {
        String result = getFirstGroup(content, regex);
        return result != null ? result : defaultValue;
    }

    // Retrieves the first matching group from the content based on any of the provided regex options or returns the default value if not found
    public static String getFirstGroupOrDefault(String content, String[] regexOptions, String defaultValue) {
        for (String currentRegex: regexOptions) {
            String result = getFirstGroup(content, currentRegex);
            if (result != null) {
                return result;
            }
        }
        return defaultValue;
    }

    // Converts a string to an integer, keeping only numeric characters
    public static int stringToInteger(String input) {
        info("Converting string of " + input + " to int");
        String onlyNumeric = input.replaceAll("[^0-9.-]", "");
        return (int)Double.parseDouble(onlyNumeric);
    }

    // Converts a string to a double, keeping only numeric characters
    public static double stringToDouble(String input) {
        info("Converting string of " + input + " to double");
        String onlyNumeric = input.replaceAll("[^0-9.-]", "");
        return Double.parseDouble(onlyNumeric);
    }

    // Converts a string to a float, keeping only numeric characters
    public static float stringToFloat(String input) {
        info("Converting string of " + input + " to float");
        String onlyNumeric = input.replaceAll("[^0-9.-]", "");
        return Float.parseFloat(onlyNumeric);
    }

    // Validates if the input string is a valid wallet address
    public static boolean isValidWalletAddress(String input) {
        return isValidValueByRegex(input, "^0x[a-fA-F0-9]{40}$");
    }

    // Validates if the input string matches the provided regex
    public static boolean isValidValueByRegex(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.find();
    }

    // Extracts the test name from the class name using regex
    public static String getTestNameFromClassName(String className) {
        return getFirstGroup(className, "(AUT[\\d]+)_");
    }

    // Extracts the domain name from the class name using regex
    public static String getDomainNameFromClassName(String className) {
        String result = getFirstGroup(className, "AUT[\\d]+_([A-Za-z]{2,9})_");
        if (result != null) {
            return result;
        }
        return getFirstGroup(className, "cicd\\.([A-Z]{2,3})\\.");
    }

    // Returns an empty string if the input string is null
    public static String blankIfNull(String s) {
        return s == null ? "" : s;
    }


    // Replaces all white spaces in the input string with the specified character
    public static String replaceWhiteSpaceForCharacter(String s, String ch) {
        return s.replaceAll(" ", ch);
    }
}
