package io.porko.member.controller.model;

public enum AvailabilityStatus {
    AVAILABLE, UNAVAILABLE;

    public static AvailabilityStatus valueOf(boolean isDuplicated) {
        if (isDuplicated) {
            return UNAVAILABLE;
        }
        return AVAILABLE;
    }
}
