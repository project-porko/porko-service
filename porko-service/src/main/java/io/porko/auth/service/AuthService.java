package io.porko.auth.service;

import static io.porko.auth.exception.AuthErrorCode.BAD_CREDENTIALS;

import io.porko.auth.controller.model.LoginRequest;
import io.porko.auth.controller.model.LoginResponse;
import io.porko.auth.controller.model.PorkoPrincipal;
import io.porko.auth.exception.AuthException;
import io.porko.member.domain.Member;
import io.porko.member.repo.MemberRepo;
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
        PorkoPrincipal porkoPrincipal = verifyMember(loginRequest);
        String accessToken = tokenService.issueAccessToken(porkoPrincipal);

        return LoginResponse.of(porkoPrincipal, accessToken);
    }

    private PorkoPrincipal verifyMember(LoginRequest loginRequest) {
        Member member = loadUserByUsername(loginRequest.email());
        checkPasswordIsMatched(loginRequest.password(), member.getPassword());

        return PorkoPrincipal.of(member.getId(), member.getEmail());
    }

    public Member loadUserByUsername(String email) {
        return memberRepo.findByEmail(email)
            .orElseThrow(() -> new AuthException(BAD_CREDENTIALS));
    }

    private void checkPasswordIsMatched(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new AuthException(BAD_CREDENTIALS);
        }
    }
}
