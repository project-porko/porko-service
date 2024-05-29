package io.porko.widget.controller.model;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import io.porko.widget.exception.WidgetErrorCode;
import io.porko.widget.exception.WidgetException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Model:ModifyMemberWidgetOrderDto")
class ModifyMemberWidgetOrderDtoTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    @DisplayName("순서 범위가 1~6이 아닌 위젯이 포함된 경우")
    void invalidSequenceRange(final int given) {
        // When & Then
        assertThatExceptionOfType(WidgetException.class)
            .isThrownBy(() -> new ModifyMemberWidgetOrderDto(1L, given))
            .extracting(WidgetException::getCode)
            .isEqualTo(WidgetErrorCode.INVALID_SEQUENCE_RANGE.name())
        ;
    }
}
