package io.porko.member.controller.model;

public record ValidateDuplicateRequest(
    String memberId,
    String email
) {
    public static ValidateDuplicateRequest of(String memberId, String email) {
        return new ValidateDuplicateRequest(memberId, email);
    }
}
