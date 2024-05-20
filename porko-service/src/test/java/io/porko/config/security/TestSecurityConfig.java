package io.porko.config.security;

import static io.porko.auth.domain.TokenProvider.ID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import io.porko.auth.config.SecurityConfig;
import io.porko.auth.config.jwt.JwtProperties;
import io.porko.auth.controller.model.PorkoPrincipal;
import io.porko.auth.service.AuthService;
import io.porko.member.domain.Address;
import io.porko.member.domain.Gender;
import io.porko.member.domain.Member;
import jakarta.annotation.Resource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.util.ReflectionTestUtils;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean
    private AuthService authService;

    @MockBean
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Resource
    public JwtProperties jwtProperties;

    @BeforeTestMethod
    public void setUp() {
        ReflectionTestUtils.setField(testMember, ID, TEST_PORKO_ID);

        given(inMemoryUserDetailsManager.loadUserByUsername(anyString()))
            .willReturn(testPorkoPrincipal);
    }

    public static final Long TEST_PORKO_ID = 1L;
    public static final String TEST_PORKO_MEMBER_ID = "testPorkoMemberId";

    public static final PorkoPrincipal testPorkoPrincipal = PorkoPrincipal.of(TEST_PORKO_ID, TEST_PORKO_MEMBER_ID);

    public static final Member testMember = Member.of(
        TEST_PORKO_MEMBER_ID,
        "testPorkoMemberPassword",
        "testPorkorer",
        "testPorkorer@porko.info",
        "01011112222",
        Address.of("서울시 서초구", "강남"),
        Gender.MALE
    );
}
