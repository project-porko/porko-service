package io.porko.member.facade.dto;

import static io.porko.utils.ConvertUtils.convertToConstants;

import java.util.Map.Entry;

public record ValidateDuplicateRequestField(
    ValidateDuplicateRequestType requestType,
    String value
) {
    public static ValidateDuplicateRequestField of(Entry<String, Object> entry) {
        ValidateDuplicateRequestType requestType = toValidateType(entry.getKey());
        String value = castToString(entry.getValue());
        requestType.validateFormat(value);

        return new ValidateDuplicateRequestField(
            requestType,
            value
        );
    }

    private static ValidateDuplicateRequestType toValidateType(String requestField) {
        String ValidateDuplicateRequestTypeConstants = convertToConstants(requestField);
        return ValidateDuplicateRequestType.valueOf(ValidateDuplicateRequestTypeConstants);
    }

    public static String castToString(Object value) {
        return (String) value;
    }
}
