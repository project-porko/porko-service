package io.porko.member.controller;

import static io.porko.member.controller.MemberController.MEMBER_BASE_URI;

import io.porko.auth.controller.model.LoginMember;
import io.porko.member.controller.model.MemberResponse;
import io.porko.member.controller.model.signup.SignUpRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateType;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateTypeResponse;
import io.porko.member.facade.ValidateDuplicateFacade;
import io.porko.member.service.MemberService;
import io.porko.utils.ResponseEntityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(
    value = MEMBER_BASE_URI,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
@RequiredArgsConstructor
public class MemberController {
    static final String MEMBER_BASE_URI = "/member";
    private static final String GET_AN_MEMBER_URI_FORMAT = MEMBER_BASE_URI.concat("/{id}");

    private final ValidateDuplicateFacade validateDuplicateFacade;
    private final MemberService memberService;

    @GetMapping("validate/types")
    ResponseEntity<ValidateDuplicateTypeResponse> getValidateDuplicateTypes() {
        return ResponseEntity.ok(ValidateDuplicateTypeResponse.create());
    }

    @GetMapping("validate")
    ResponseEntity<ValidateDuplicateResponse> validateDuplicate(
        @RequestParam(name = "type", required = false) ValidateDuplicateType requestType,
        @RequestParam(name = "value", required = false) String value
    ) {
        ValidateDuplicateRequest request = ValidateDuplicateRequest.of(requestType, value);
        ValidateDuplicateResponse response = validateDuplicateFacade.isDuplicated(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("sign-up")
    ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntityUtils.created(
            GET_AN_MEMBER_URI_FORMAT,
            memberService.createMember(signUpRequest)
        );
    }

    @GetMapping("me")
    ResponseEntity<MemberResponse> me(@LoginMember Long loginMemberId) {
        MemberResponse memberResponse = memberService.loadMemberById(loginMemberId);
        return ResponseEntity.ok(memberResponse);
    }
}
