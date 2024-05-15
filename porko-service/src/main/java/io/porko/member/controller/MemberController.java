package io.porko.member.controller;

import static io.porko.member.controller.MemberController.MEMBER_BASE_URI;

import io.porko.member.controller.model.SignUpRequest;
import io.porko.member.controller.model.ValidateDuplicateRequest;
import io.porko.member.controller.model.ValidateDuplicateResponse;
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

    @GetMapping("validate")
    ResponseEntity<ValidateDuplicateResponse> validateDuplicate(
        @RequestParam(name = "memberId", required = false) String memberId,
        @RequestParam(name = "email", required = false) String email
    ) {
        System.out.println(memberId);
        ValidateDuplicateResponse duplicated = validateDuplicateFacade.isDuplicated(
            ValidateDuplicateRequest.of(memberId, email)
        );
        return ResponseEntity.ok(duplicated);
    }

    @PostMapping("sign-up")
    ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntityUtils.created(
            GET_AN_MEMBER_URI_FORMAT,
            memberService.createMember(signUpRequest)
        );
    }
}
