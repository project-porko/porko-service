package io.porko.utils;

public class ConvertUtils {
    public static String convertToConstants(String value) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";

        return value.replaceAll(regex, replacement)
            .toUpperCase();
    }
}
