package io.porko.domain.widget.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import io.porko.domain.widget.exception.WidgetErrorCode;
import io.porko.domain.widget.exception.WidgetException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Domain:Sequence")
class SequenceTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    @DisplayName("순서 범위가 1~6이 아닌 위젯이 포함된 경우")
    void outOfSequenceRange(final int given) {
        // When & Then
        assertThatExceptionOfType(WidgetException.class)
            .isThrownBy(() -> Sequence.orderedFrom(given))
            .extracting(WidgetException::getErrorCode)
            .isEqualTo(WidgetErrorCode.OUT_OF_SEQUENCE_RANGE)
        ;
    }
}
