package io.porko.domain.auth.service;

import io.porko.domain.auth.controller.model.LoginRequest;
import io.porko.domain.auth.controller.model.LoginResponse;
import io.porko.domain.auth.controller.model.PorkoPrincipal;
import io.porko.domain.auth.exception.AuthException;
import io.porko.domain.auth.exception.AuthErrorCode;
import io.porko.domain.member.domain.Member;
import io.porko.domain.member.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        Member member = loadUserByUsername(loginRequest.email());
        PorkoPrincipal porkoPrincipal = verifyMember(loginRequest, member);
        String accessToken = tokenService.issueAccessToken(porkoPrincipal);

        return LoginResponse.of(member, accessToken);
    }

    private PorkoPrincipal verifyMember(LoginRequest loginRequest, Member member) {
        checkPasswordIsMatched(loginRequest.password(), member.getPassword());
        return PorkoPrincipal.of(member.getId(), member.getEmail());
    }

    public Member loadUserByUsername(String email) {
        return memberRepo.findByEmail(email)
            .orElseThrow(() -> new AuthException(AuthErrorCode.BAD_CREDENTIALS));
    }

    private void checkPasswordIsMatched(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new AuthException(AuthErrorCode.BAD_CREDENTIALS);
        }
    }
}
