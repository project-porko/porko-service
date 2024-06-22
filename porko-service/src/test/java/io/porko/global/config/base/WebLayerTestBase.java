package io.porko.global.config.base;

import io.porko.config.base.presentation.WebMvcTestBase;
import io.porko.domain.auth.controller.AuthController;
import io.porko.domain.auth.domain.JwtProperties;
import io.porko.domain.auth.service.AuthService;
import io.porko.domain.member.controller.MemberController;
import io.porko.domain.member.facade.ValidateDuplicateFacade;
import io.porko.domain.member.service.MemberService;
import io.porko.domain.widget.controller.MemberWidgetController;
import io.porko.domain.widget.controller.WidgetController;
import io.porko.domain.widget.service.MemberWidgetService;
import io.porko.domain.widget.service.WidgetService;
import io.porko.global.config.cache.CacheConfig;
import io.porko.global.config.security.TestSecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

@WebMvcTest(
    controllers = {
        AuthController.class,
        MemberController.class,
        MemberWidgetController.class,
        WidgetController.class
    }
)
@Import({TestSecurityConfig.class, CacheConfig.class})
// TODO WebLayerTest(Bean based Test)와 WebMvcTest(MockMvc를 이용한 Controller layer test) test base 분리
// TODO 명확하지 못한 class명 변경 WebLayerTest -> ServiceTestBase, WebMvcTest -> ControllerTestBase
// TODO ServiceTestBase : CacheManager, ValidateDuplicateFacade, @Import(CacheConfig)
// TODO ControllerTestBase : *Controllers, @Import(TestSecurityConfig)
public abstract class WebLayerTestBase extends WebMvcTestBase {
    @SpyBean
    protected JwtProperties jwtProperties;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected ValidateDuplicateFacade validateDuplicateFacade;

    @MockBean
    protected WidgetService widgetService;

    @MockBean
    protected MemberWidgetService memberWidgetService;
}
