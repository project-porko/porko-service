package io.porko.widget.domain;

import static io.porko.config.fixture.FixtureCommon.dtoType;
import static io.porko.config.security.TestSecurityConfig.testMember;
import static io.porko.widget.controller.WidgetControllerTestHelper.allWidgets;
import static io.porko.widget.controller.model.ReorderWidgetRequest.ORDERED_WIDGET_COUNT;
import static io.porko.widget.fixture.MemberWidgetFixture.valiedReorderWidgetRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import io.porko.config.base.TestBase;
import io.porko.widget.controller.model.ModifyMemberWidgetOrderDto;
import io.porko.widget.controller.model.ReorderWidgetRequest;
import io.porko.widget.exception.WidgetErrorCode;
import io.porko.widget.exception.WidgetException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Domain:OrderedMemberWidgets")
class OrderedMemberWidgetsTest extends TestBase {
    @Nested
    @DisplayName("사용자의 위젯 순서 변경 시 예외 발생")
    class ThrowsWidgetException {
        @Test
        @DisplayName("순서를 변경할 위젯 목록에 고정 타입의 위젯이 포함된 경우")
        void includeFixedWidget() {
            // Given
            List<ModifyMemberWidgetOrderDto> reorderWidgets = dtoType()
                .giveMeBuilder(ModifyMemberWidgetOrderDto.class)
                .setLazy("widgetId", TestBase::nextLong)
                .setLazy("sequence", TestBase::nextInt)
                .sampleList(ORDERED_WIDGET_COUNT);

            ReorderWidgetRequest reorderWidgetRequest = new ReorderWidgetRequest(reorderWidgets);

            // When
            assertThatExceptionOfType(WidgetException.class)
                .isThrownBy(() -> OrderedMemberWidgets.of(testMember, allWidgets, reorderWidgetRequest))
                .extracting(WidgetException::getCode)
                .isEqualTo(WidgetErrorCode.INCLUDE_FIXED_WIDGET.name());
        }

        @Test
        @DisplayName("순서를 변경할 위젯 목록에 존재하지 않는 위젯이 포함된 경우")
        void includeNotExistWidget() {
            // Given
            AtomicReference<Long> longAtomicReference = new AtomicReference<>(8L);
            List<ModifyMemberWidgetOrderDto> reorderWidgets = dtoType()
                .giveMeBuilder(ModifyMemberWidgetOrderDto.class)
                .setLazy("widgetId", () -> nextLong(longAtomicReference))
                .setLazy("sequence", TestBase::nextInt)
                .sampleList(ORDERED_WIDGET_COUNT);

            ReorderWidgetRequest reorderWidgetRequest = new ReorderWidgetRequest(reorderWidgets);

            // When & Then
            assertThatExceptionOfType(WidgetException.class)
                .isThrownBy(() -> OrderedMemberWidgets.of(testMember, allWidgets, reorderWidgetRequest))
                .extracting(WidgetException::getCode)
                .isEqualTo(WidgetErrorCode.INCLUDE_NOT_EXIST_WIDGET.name());
        }
    }

    @Test
    @DisplayName("회원의 위젯 순서 변경 요청 시 순서가 지정된 위젯으로 변경")
    void create() {
        // When
        OrderedMemberWidgets actual = OrderedMemberWidgets.of(testMember, allWidgets, valiedReorderWidgetRequest);

        // Then
        List<Integer> actualSequence = actual.getSequencedWidgets().stream()
            .map(MemberWidget::getSequence)
            .collect(Collectors.toList());

        assertThat(actual.size()).isEqualTo(allWidgets.elements().size());
        assertThat(actual.getSequencedWidgets().size()).isEqualTo(ORDERED_WIDGET_COUNT);
        assertThat(actual.getUnSequencedWidgets().size()).isEqualTo(ORDERED_WIDGET_COUNT + 1);
        assertThat(actualSequence).isEqualTo(List.of(1, 2, 3, 4, 5, 6));
    }
}