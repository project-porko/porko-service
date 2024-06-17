package io.porko.global.config.base;

import io.porko.config.base.TestBase;
import io.porko.domain.member.facade.ValidateDuplicateFacade;
import io.porko.domain.member.facade.strategy.EmailDuplicateStrategy;
import io.porko.domain.member.facade.strategy.PhoneNumberDuplicateStrategy;
import io.porko.domain.member.service.MemberService;
import io.porko.global.config.cache.CacheConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;

// TODO WebMvcTest를 이용한 Bean 등록 개선하기 https://docs.spring.io/spring-boot/docs/2.0.0.M5/reference/html/boot-features-testing.html
// TODO WebMvcTest를 통해 등록되는 WebLayerBean 말고 Service 계층 관련 Bean만 등록할 수 있는 방법 찾아보기(Test 경량화)
@WebMvcTest(controllers = {
    ValidateDuplicateFacade.class,
    CacheManager.class
})
@Import(CacheConfig.class)
public class ServiceBeanTestBase extends TestBase {
    @MockBean
    private MemberService memberService;

    @SpyBean
    private PhoneNumberDuplicateStrategy phoneNumberDuplicateStrategy;

    @SpyBean
    private EmailDuplicateStrategy emailDuplicateStrategy;

    @SpyBean
    protected CacheManager cacheManager;
}
