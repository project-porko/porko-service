package io.porko.member.service;

import static io.porko.member.exception.MemberErrorCode.DUPLICATED_EMAIL;
import static io.porko.member.exception.MemberErrorCode.DUPLICATED_MEMBER_ID;
import static io.porko.member.exception.MemberErrorCode.DUPLICATED_PHONE_NUMBER;

import io.porko.member.controller.model.MemberResponse;
import io.porko.member.controller.model.signup.SignUpRequest;
import io.porko.member.exception.MemberErrorCode;
import io.porko.member.exception.MemberException;
import io.porko.member.repo.MemberQueryRepo;
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
    private final MemberQueryRepo memberQueryRepo;
    private final PasswordEncoder passwordEncoder;

    public boolean isDuplicatedMemberId(String memberId) {
        return memberRepo.existsByMemberId(memberId);
    }

    public boolean isDuplicatedEmail(String email) {
        return memberRepo.existsByEmail(email);
    }

    public boolean isDuplicatedPhoneNumber(String phoneNumber) {
        return memberRepo.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public Long createMember(SignUpRequest signUpRequest) {
        checkDuplicatedMemberId(signUpRequest.memberId());
        checkDuplicatedEmail(signUpRequest.email());
        checkDuplicatePhoneNumber(signUpRequest.phoneNumber());
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

    private void checkDuplicatePhoneNumber(String phoneNumber) {
        if (isDuplicatedPhoneNumber(phoneNumber)) {
            throw new MemberException(DUPLICATED_PHONE_NUMBER, phoneNumber);
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public MemberResponse loadMemberById(Long id) {
        return memberQueryRepo.findMemberById(id)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND, id));
    }
}
