package io.porko.widget.domain;

import static io.porko.widget.domain.OrderedMemberWidgets.ORDERED_WIDGET_COUNT;
import static io.porko.widget.exception.WidgetErrorCode.OUT_OF_SEQUENCE_RANGE;

import io.porko.widget.exception.WidgetException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;

@Getter
@Embeddable
@AccessType(Type.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sequence {
    private static final int MAX_LENGTH = 1;
    private static final int OPTIONAL_SEQUENCE = -1;
    private static final int SEQUENCE_START = 1;
    private static final int SEQUENCE_END = ORDERED_WIDGET_COUNT;

    @Column(nullable = false, length = 1)
    private int value;

    public static Sequence orderedFrom(int target) {
        validateSequence(target);
        return new Sequence(target);
    }

    private static void validateSequence(int target) {
        if (isNotSequenceRange(target)) {
            throw new WidgetException(OUT_OF_SEQUENCE_RANGE, target);
        }
    }

    private static boolean isNotSequenceRange(Integer sequence) {
        return sequence < SEQUENCE_START || sequence > SEQUENCE_END;
    }

    public static Sequence unorderedSequence() {
        return new Sequence(OPTIONAL_SEQUENCE);
    }

    public boolean isUnsequenced() {
        return value == OPTIONAL_SEQUENCE;
    }
}
