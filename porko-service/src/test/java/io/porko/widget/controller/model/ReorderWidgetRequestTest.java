package io.porko.widget.controller.model;

import static io.porko.widget.controller.model.ReorderWidgetRequest.ORDERED_WIDGET_COUNT;
import static io.porko.widget.fixture.MemberWidgetFixture.givenBuilder;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import io.porko.config.base.TestBase;
import io.porko.widget.exception.WidgetErrorCode;
import io.porko.widget.exception.WidgetException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Model:Request:ModifyMemberWidgetsOrder")
class ReorderWidgetRequestTest extends TestBase {
    @Nested
    @DisplayName("회원의 위젯 순서 변경 요청 시 예외 발생")
    class ExceptionTest {
        @Test
        @DisplayName("순서를 변경할 위젯의 개수가 6개가 아닌 경우")
        void invalidCount() {
            // When & Then
            assertThatExceptionOfType(WidgetException.class)
                .isThrownBy(() -> new ReorderWidgetRequest(givenBuilder.sampleList(1)))
                .extracting(WidgetException::getErrorCode)
                .isEqualTo(WidgetErrorCode.INVALID_REQUEST_ORDERED_WIDGET_COUNT);
        }

        @Test
        @DisplayName("중복된 순서를 가진 위젯이 포함된 경우")
        void duplicatedSequence() {
            // Given
            List<ModifyMemberWidgetOrderDto> given = givenBuilder
                .set("sequence", 1, 2)
                .sampleList(ORDERED_WIDGET_COUNT);

            System.out.println(given.size());

            // When & Then
            assertThatExceptionOfType(WidgetException.class)
                .isThrownBy(() -> new ReorderWidgetRequest(given))
                .extracting(WidgetException::getErrorCode)
                .isEqualTo(WidgetErrorCode.DUPLICATED_SEQUENCE);
        }
    }
}
