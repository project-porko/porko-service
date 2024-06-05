package io.porko.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConvertUtils {
    public static String convertToConstants(String value) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";

        return value.replaceAll(regex, replacement)
            .toUpperCase();
    }

    public static LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(date, formatter);
    }
}
