package io.porko.widget.controller.model;

import static io.porko.config.fixture.FixtureCommon.dtoType;
import static io.porko.config.fixture.FixtureCommon.entityType;
import static io.porko.widget.controller.WidgetControllerTestHelper.widgetsResponse;
import static io.porko.widget.controller.model.ModifyMemberWidgetsOrderRequest.ORDERED_WIDGET_COUNT;
import static io.porko.widget.fixture.MemberWidgetFixture.targetWidgets;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import io.porko.config.base.TestBase;
import io.porko.member.domain.Member;
import io.porko.widget.domain.MemberWidget;
import io.porko.widget.domain.OrderedMemberWidgets;
import io.porko.widget.domain.Widget;
import io.porko.widget.exception.WidgetErrorCode;
import io.porko.widget.exception.WidgetException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

// TODO: 테스트가 어려운 해당 Class의 설계가 잘 못되진 않았는지 고민 후 리팩토링
@DisplayName("Model:Request:ModifyMemberWidgetsOrder")
class ModifyMemberWidgetsOrderRequestTest extends TestBase {
    private final ArbitraryBuilder<ModifyMemberWidgetOrderDto> givenBuilder = dtoType()
        .giveMeBuilder(ModifyMemberWidgetOrderDto.class);

    private final ArbitraryBuilder<Widget> widgetBuilder = dtoType()
        .giveMeBuilder(Widget.class);

    @Nested
    @DisplayName("회원의 위젯 순서 변경 요청 시 예외 발생")
    class ExceptionTest {
        @Test
        @DisplayName("순서를 변경할 위젯의 개수가 6개가 아닌 경우")
        void invalidCount() {
            // When & Then
            assertThatExceptionOfType(WidgetException.class)
                .isThrownBy(() -> new ModifyMemberWidgetsOrderRequest(givenBuilder.sampleList(1)))
                .extracting(WidgetException::getCode)
                .isEqualTo(WidgetErrorCode.INVALID_REQUEST_ORDERED_WIDGET_COUNT.name())
            ;
        }

        @Test
        @DisplayName("중복된 순서를 가진 위젯이 포함된 경우")
        void duplicatedSequence() {
            // Given
            List<ModifyMemberWidgetOrderDto> given = givenBuilder
                .set("sequence", 1, 2)
                .sampleList(ORDERED_WIDGET_COUNT);

            // When & Then
            assertThatExceptionOfType(WidgetException.class)
                .isThrownBy(() -> new ModifyMemberWidgetsOrderRequest(given))
                .extracting(WidgetException::getCode)
                .isEqualTo(WidgetErrorCode.DUPLICATED_SEQUENCE.name())
            ;
        }

        @Test
        @DisplayName("순서를 변경할 위젯 목록에 고정 타입의 위젯이 포함된 경우")
        void includeFixedTypeWidget() {
            // Given
            Member member = entityType().giveMeOne(Member.class);

            List<ModifyMemberWidgetOrderDto> targetWidget = givenBuilder
                .setLazy("widgetId", () -> nextId())
                .setLazy("sequence", () -> nextIndex())
                .sampleList(ORDERED_WIDGET_COUNT);

            List<Long> collect = targetWidget.stream()
                .map(it -> it.widgetId())
                .collect(Collectors.toList());

            List<Widget> fromWidgets = widgetsResponse.extractByIds(collect);

            ModifyMemberWidgetsOrderRequest given = new ModifyMemberWidgetsOrderRequest(targetWidget);

            // When & Then
            assertThatExceptionOfType(WidgetException.class)
                .isThrownBy(() -> given.toEntity(member, fromWidgets))
                .extracting(WidgetException::getCode)
                .isEqualTo(WidgetErrorCode.INCLUDE_FIXED_WIDGET.name())
            ;
        }

        @Test
        @DisplayName("순서를 변경할 위젯 목록에 존재하지 않는 위젯이 포함된 경우")
        void IncludeNotExistWidget() {
            // Given
            Member member = entityType().giveMeOne(Member.class);
            AtomicReference<Long> longAtomicReference = new AtomicReference<>(8L);
            List<ModifyMemberWidgetOrderDto> targetWidgets = givenBuilder
                .setLazy("widgetId", () -> nextId(longAtomicReference))
                .setLazy("sequence", () -> nextIndex())
                .sampleList(ORDERED_WIDGET_COUNT);

            List<Long> targetWidgetIds = targetWidgets.stream().map(it -> it.widgetId()).collect(Collectors.toList());
            List<Widget> widgets = widgetsResponse.extractByIds(targetWidgetIds);

            ModifyMemberWidgetsOrderRequest given = new ModifyMemberWidgetsOrderRequest(targetWidgets);

            // When & Then
            assertThatExceptionOfType(WidgetException.class)
                .isThrownBy(() -> given.toEntity(member, widgets))
                .extracting(WidgetException::getCode)
                .isEqualTo(WidgetErrorCode.INCLUDE_NOT_EXIST_WIDGET.name())
            ;
        }
    }

    @Test
    @DisplayName("회원의 위젯 순서 변경 요청 시 순서가 지정된 위젯으로 변경")
    void toEntity() {
        // Given
        Member member = entityType().giveMeOne(Member.class);
        List<ModifyMemberWidgetOrderDto> targetWidgets = targetWidgets();

        List<Long> targetWidgetIds = targetWidgets.stream().map(it -> it.widgetId()).collect(Collectors.toList());
        List<Integer> expected = targetWidgets.stream().map(it -> it.sequence()).collect(Collectors.toList());

        List<Widget> widgets = widgetsResponse.extractByIds(targetWidgetIds);

        // When
        ModifyMemberWidgetsOrderRequest given = new ModifyMemberWidgetsOrderRequest(targetWidgets);
        OrderedMemberWidgets actual = given.toEntity(member, widgets);

        // Then
        List<Integer> actualSequences = actual.elements()
            .stream()
            .map(MemberWidget::getSequence)
            .collect(Collectors.toList());

        assertThat(actualSequences).isEqualTo(expected);
    }
}
