package io.porko.widget.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domain:Widgets")
class WidgetsTest {
    @Test
    @DisplayName("신규 회원에 초기화활 기본값이 부여된 위젯 목록을 생성")
    void createInitialWidgets() {
        // When
        Widgets actual = Widgets.initialWidgets();

        // Then
        List<Widget> expected = Arrays.stream(WidgetCode.values()).map(Widget::from).toList();
        assertThat(actual.elements()).containsAll(expected);
    }
}
