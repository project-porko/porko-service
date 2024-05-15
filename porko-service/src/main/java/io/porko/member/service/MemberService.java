package io.porko.member.service;

import static io.porko.member.exception.MemberErrorCode.DUPLICATED_EMAIL;
import static io.porko.member.exception.MemberErrorCode.DUPLICATED_MEMBER_ID;

import io.porko.member.controller.model.signup.SignUpRequest;
import io.porko.member.exception.MemberException;
import io.porko.member.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;

    public boolean isDuplicatedMemberId(String memberId) {
        return memberRepo.existsByMemberId(memberId);
    }

    public boolean isDuplicatedEmail(String email) {
        return memberRepo.existsByEmail(email);
    }

    @Transactional
    public Long createMember(SignUpRequest signUpRequest) {
        checkDuplicatedMemberId(signUpRequest.memberId());
        checkDuplicatedEmail(signUpRequest.email());
        String encryptedPassword = encryptPassword(signUpRequest.password());

        return memberRepo.save(signUpRequest.toEntity(encryptedPassword)).getId();
    }

    private void checkDuplicatedMemberId(String memberId) {
        if (isDuplicatedMemberId(memberId)) {
            throw new MemberException(DUPLICATED_MEMBER_ID, memberId);
        }
    }

    private void checkDuplicatedEmail(String email) {
        if (isDuplicatedEmail(email)) {
            throw new MemberException(DUPLICATED_EMAIL, email);
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
