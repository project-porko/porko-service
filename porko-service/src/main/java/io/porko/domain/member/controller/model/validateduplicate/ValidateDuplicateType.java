package io.porko.domain.member.controller.model.validateduplicate;

import static java.util.regex.Pattern.compile;

import io.porko.domain.member.exception.MemberErrorCode;
import io.porko.domain.member.exception.MemberException;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public enum ValidateDuplicateType {
    EMAIL(compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"), "이메일"),
    PHONE_NUMBER(compile("^\\d{11}$"), "휴대폰 번호");

    private final Pattern pattern;
    private final String description;

    ValidateDuplicateType(Pattern pattern, String description) {
        this.pattern = pattern;
        this.description = description;
    }

    public void validateFormat(String value) {
        if (value == null || isNotMatched(value)) {
            throw new MemberException(MemberErrorCode.INVALID_VALIDATE_DUPLICATE_VALUE_FORMAT, value);
        }
    }

    private boolean isNotMatched(String value) {
        return !pattern.matcher(value).matches();
    }
}
