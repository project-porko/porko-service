package io.porko.config.base;

import io.porko.auth.config.jwt.JwtProperties;
import io.porko.auth.controller.AuthController;
import io.porko.auth.service.AuthService;
import io.porko.config.base.presentation.WebMvcTestBase;
import io.porko.config.cache.CacheConfig;
import io.porko.config.security.TestSecurityConfig;
import io.porko.member.controller.MemberController;
import io.porko.member.facade.ValidateDuplicateFacade;
import io.porko.member.service.MemberService;
import io.porko.widget.controller.MemberWidgetController;
import io.porko.widget.controller.WidgetController;
import io.porko.widget.service.MemberWidgetService;
import io.porko.widget.service.WidgetService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;

@WebMvcTest(
    controllers = {
        AuthController.class,
        MemberController.class,
        MemberWidgetController.class,
        WidgetController.class,
        CacheManager.class
    }
)
@Import({TestSecurityConfig.class, CacheConfig.class})
public abstract class WebLayerTestBase extends WebMvcTestBase {
    @SpyBean
    protected JwtProperties jwtProperties;

    @SpyBean
    protected CacheManager cacheManager;

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
