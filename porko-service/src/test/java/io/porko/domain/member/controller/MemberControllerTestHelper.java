package io.porko.domain.member.controller;

import io.porko.config.base.presentation.RequestBuilder.Expect;
import io.porko.domain.member.controller.model.signup.AddressDto;
import io.porko.domain.member.controller.model.signup.SignUpRequest;
import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateType;
import io.porko.global.config.base.WebLayerTestBase;

class MemberControllerTestHelper extends WebLayerTestBase {
    private static final String MEMBER_SIGN_UP_URI = "/member/sign-up";
    private static final String MEMBER_SIGN_UP_VALIDATE_DUPLICATE_URL = "/member/validate?type={type}&value={value}";
    private static final String ME_URI = "/member/me";
    protected final SignUpRequest 유효_하지_않은_회원_가입_요청_객체 = new SignUpRequest(
        "",
        "",
        "",
        "",
        new AddressDto("", ""),
        null,
        "https://avatars.githubusercontent.com/u/169456863?s=200&v=4"
    );

    protected Expect 회원_가입_요청(SignUpRequest 회원_가입_요청_정보) {
        return post()
            .url(MEMBER_SIGN_UP_URI)
            .noAuthentication()
            .jsonContent(회원_가입_요청_정보);
    }

    protected Expect 중복_검사_요청(ValidateDuplicateType 중복_검사_타입, String 중복_검사_요청값) {
        return get()
            .url(MEMBER_SIGN_UP_VALIDATE_DUPLICATE_URL, 중복_검사_타입, 중복_검사_요청값)
            .noAuthentication()
            .expect();
    }

    protected Expect 내_정보_조회() {
        return get()
            .url(ME_URI)
            .noAuthentication()
            .expect();
    }
}
