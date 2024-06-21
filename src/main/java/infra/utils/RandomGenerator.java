package infra.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static infra.reporting.MultiReporter.info;


public class RandomGenerator {
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static int randInt() {
        Random rand = new Random();
        return rand.nextInt();
    }

    public static String randomString(int numOfChars, int...randIntMaxType) {
        int actualRandIntMaxType = (randIntMaxType.length == 0) ? 2 : randIntMaxType[0];
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < numOfChars; i++) {

            int randInt = randInt(0,actualRandIntMaxType);

            switch (randInt) {
                case 0:
                    randomString.append((char) randInt(97, 122)); // a-z
                    break;
                case 1:
                    randomString.append((char) randInt(65, 90)); // A-Z
                    break;
                case 2:
                    randomString.append(randInt(0, 9)); // 0-9
                    break;
            }
        }

        return randomString.toString();
    }

    public static String randomStringContainingOnlyLetters(int numOfChars) {
        return randomString(numOfChars,1 );
    }


    public static String randomNumber(int numberOfDigits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfDigits; i++) {
            sb.append(randInt(0, 9)); // 0-9
        }

        info("Generated random number: " + sb.toString());
        return sb.toString();
    }

    public static String randomDate(String datePattern) {
        int minAge = 18*12*31;
        int maxAge = 80*12*31;
        LocalDate birthday = LocalDate.now().minusDays(new Random().nextInt(maxAge-minAge)+minAge);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String generatedDate = formatter.format(birthday);
        info("Generated random date: " + generatedDate);
        return generatedDate;
    }
}
