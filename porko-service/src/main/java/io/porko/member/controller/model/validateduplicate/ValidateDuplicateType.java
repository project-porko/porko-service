package io.porko.member.controller.model.validateduplicate;

import static java.util.regex.Pattern.compile;

import io.porko.member.exception.MemberErrorCode;
import io.porko.member.exception.MemberException;
import java.util.regex.Pattern;

public enum ValidateDuplicateType {
    MEMBER_ID(compile("^[a-zA-Z0-9]{6,20}$")),
    EMAIL(compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));

    private final Pattern pattern;

    ValidateDuplicateType(Pattern pattern) {
        this.pattern = pattern;
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
