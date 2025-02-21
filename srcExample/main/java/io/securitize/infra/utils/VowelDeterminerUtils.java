package io.securitize.infra.utils;

import java.util.Arrays;
import java.util.List;

public final class VowelDeterminerUtils {
    private static final List<String> vowelLetters = Arrays.asList("a", "e", "i", "o", "u");

    private VowelDeterminerUtils() {
    }

    public static String setAAnDeterminer(String word) {
        if (word.equalsIgnoreCase("all")) {
            return "";
        }
        if (startsWithVowelLetter(word) || isletterHPronouncedAsVowel(word)) {
            return "an";
        } else {
            return "a";
        }
    }

    private static boolean startsWithVowelLetter(String word) {
        for (String vowelLetter : vowelLetters) {
            if (word.toLowerCase().startsWith(vowelLetter))
                return true;
        }
        return false;
    }

    private static boolean isletterHPronouncedAsVowel(String word) {
        // The rule of thumb for words beginning in ‘h’ is to consider the way the word is pronounced:
        // Words that have a silent ‘h’ (such as honour, hour, or honest) begin with a vowel sound, so they use ‘an’.
        return (word.length() > 2
                && word.toLowerCase().startsWith("ho"));
    }

}
