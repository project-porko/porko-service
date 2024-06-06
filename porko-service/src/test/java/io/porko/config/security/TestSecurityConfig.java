package io.porko.config.security;

import static io.porko.auth.domain.TokenProvider.ID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import io.porko.auth.config.SecurityConfig;
import io.porko.auth.controller.model.PorkoPrincipal;
import io.porko.member.domain.Address;
import io.porko.member.domain.Gender;
import io.porko.member.domain.Member;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.util.ReflectionTestUtils;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @BeforeTestMethod
    public void setUp() {
        ReflectionTestUtils.setField(testMember, ID, TEST_MEMBER_ID);

        given(inMemoryUserDetailsManager.loadUserByUsername(anyString()))
            .willReturn(testPrincipal);
    }

    public static final Long TEST_MEMBER_ID = 1L;
    public static final String TEST_MEMBER_EMAIL = "testPorkorer@porko.info";
    public static final PorkoPrincipal testPrincipal = PorkoPrincipal.of(TEST_MEMBER_ID, TEST_MEMBER_EMAIL);
    public static final Member testMember = Member.of(
        TEST_MEMBER_EMAIL,
        "testPassword",
        "tester",
        "01011112222",
        Address.of("서울시 서초구", "반포"),
        Gender.MALE
    );
}
