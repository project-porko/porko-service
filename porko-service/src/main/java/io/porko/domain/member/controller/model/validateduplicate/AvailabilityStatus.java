package io.porko.domain.member.controller.model.validateduplicate;

public enum AvailabilityStatus {
    AVAILABLE, UNAVAILABLE;

    public static AvailabilityStatus valueOf(boolean isDuplicated) {
        if (isDuplicated) {
            return UNAVAILABLE;
        }
        return AVAILABLE;
    }
}
