package io.porko.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConvertUtils {
    public static String convertToConstants(String value) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";

        return value.replaceAll(regex, replacement)
            .toUpperCase();
    }

    public static Map<String, Object> convertToMap(Object obj) {
        try {
            if (Objects.isNull(obj)) {
                return Collections.emptyMap();
            }
            Map<String, Object> convertMap = new HashMap<>();

            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                convertMap.put(field.getName(), field.get(obj));
            }
            return convertMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
