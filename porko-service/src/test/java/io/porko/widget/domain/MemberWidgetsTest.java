package io.porko.widget.domain;

import static io.porko.config.security.TestSecurityConfig.testMember;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domain:MemberWidgets")
class MemberWidgetsTest {
    @Test
    @DisplayName("신규 회원의 초기화된 위젯 목록 생성")
    void createInitialMemberWidgets() {
        // When
        MemberWidgets actual = MemberWidgets.initialOf(testMember);

        // Then
        assertThat(actual.elements())
            .extracting("sequence")
            .containsExactly(1, 2, 3, 4, 5, 6, -1, -1);
    }
}
