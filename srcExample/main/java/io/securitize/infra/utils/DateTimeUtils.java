package io.securitize.infra.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class DateTimeUtils {

    public static String currentDateTimeUTC() {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(new Date());
    }

    public static String currentDateTimeUTCWithDelta(int hoursDelta) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, hoursDelta);
        return format.format(calendar.getTime());
    }

    public static String currentDateFormat(String format) {
        return currentDate(format);
    }

    public static String currentDate(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(new Date());
    }

    public static String convertDateFormat(String value, String oldFormat, String newFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
            Date d = sdf.parse(value);
            sdf.applyPattern(newFormat);
            return sdf.format(d);
        } catch (ParseException e) {
            errorAndStop("An error occur trying to parse date from format " + oldFormat + " to format" + newFormat + " for value: " + value + ". Details: " + e, false);
            return null;
        }
    }

    public static String ticksToDateTime(long ticks) {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        Date rawDate = new Date(ticks);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(rawDate);
    }

    public static String getDaySuffix(String day){
        String[] suffixes = // 0     1     2     3     4     5     6     7     8     9
                            { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                            // 10    11    12    13    14    15    16    17    18    19
                              "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                            // 20    21    22    23    24    25    26    27    28    29
                              "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                            // 30    31
                              "th", "st" };
        return suffixes[Integer.parseInt(day)];
    }

    public static Date parseDateByFormat(String dateTimeAsString, String format, TimeZone timeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(timeZone);
        try {
            return formatter.parse(dateTimeAsString);
        } catch (ParseException e) {
            errorAndStop("Can't parse provided datetime: " + dateTimeAsString, false);
            return new Date();
        }
    }

    public static long getMinutesToMillis(long millis, int minutes) {
        long minutesToAdd = (long) minutes * 60 * 1000;
        return millis + minutesToAdd;
    }

    public static boolean validateDate(String dateDescription, String dateFormat, String actualDateAsString) {
        boolean isDateMatch = false;
        String errorMessage;
        Date currentDate = new Date();
        currentDate = DateUtils.truncate(currentDate, java.util.Calendar.DAY_OF_MONTH);
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        try {
            Date actualCreationDate = df.parse(actualDateAsString);
            if (DateUtils.isSameDay(actualCreationDate, currentDate)) {
                isDateMatch = true;
            }
        } catch (ParseException e) {
            info(dateDescription + " doesn't match");
            errorMessage = "Error parsing date: " + e.getMessage();
            errorAndStop(errorMessage, true);
        }
        return isDateMatch;
    }

    public static String convertDate(String fullDate, String actualFormat, String expectedFormat) {
        // Define input and output date-time format
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(actualFormat);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(expectedFormat);
        // Parse the input date-time string
        LocalDateTime dateTime = LocalDateTime.parse(fullDate, inputFormatter);
        // Format the date portion only
        String formattedDate = dateTime.format(outputFormatter);
        return formattedDate;
    }

}
