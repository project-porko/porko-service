package io.porko.member.controller;

import io.porko.config.base.presentation.RequestBuilder.Expect;
import io.porko.config.base.presentation.WebMvcTestBase;
import io.porko.member.controller.model.AddressDto;
import io.porko.member.controller.model.SignUpRequest;
import io.porko.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(MemberController.class)
@DisplayName("Controller:Member")
class MemberControllerTestHelper extends WebMvcTestBase {
    @MockBean
    protected MemberService memberService;

    private static final String MEMBER_SIGN_UP_URI = "/member/sign-up";
    protected final SignUpRequest 유효_하지_않은_회원_가입_요청_객체 = new SignUpRequest(
        "",
        "",
        "",
        "",
        "",
        new AddressDto("", ""),
        null
    );

    protected Expect 회원_가입_요청(SignUpRequest 회원_가입_요청_정보) {
        return post()
            .url(MEMBER_SIGN_UP_URI)
            .jsonContent(회원_가입_요청_정보);
    }
}
