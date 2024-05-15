package io.porko.exception.response.invalid;

public record ErrorDetail(
    String object,
    String field,
    String code,
    String RejectValue,
    Object rejectMessage
) {
    public static ErrorDetail of(String object, String field, String code, String RejectValue, Object rejectMessage) {
        return new ErrorDetail(object, field, code, RejectValue, rejectMessage);
    }
}
