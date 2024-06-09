package io.porko.member.service;

import static io.porko.config.event.DomainEventPublisher.registerEvent;
import static io.porko.member.exception.MemberErrorCode.DUPLICATED_EMAIL;
import static io.porko.member.exception.MemberErrorCode.DUPLICATED_PHONE_NUMBER;

import io.porko.member.controller.model.MemberResponse;
import io.porko.member.controller.model.signup.SignUpRequest;
import io.porko.member.domain.Member;
import io.porko.member.event.CreateHistoriesEvent;
import io.porko.member.event.CreateInitialMemberWidgetsEvent;
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

    public boolean isDuplicatedEmail(String email) {
        return memberRepo.existsByEmail(email);
    }

    public boolean isDuplicatedPhoneNumber(String phoneNumber) {
        return memberRepo.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public Long createMember(SignUpRequest signUpRequest) {
        checkDuplicatedEmail(signUpRequest.email());
        checkDuplicatePhoneNumber(signUpRequest.phoneNumber());
        String encryptedPassword = encryptPassword(signUpRequest.password());
        Member savedMember = memberRepo.save(signUpRequest.toEntity(encryptedPassword));

        registerEvent(CreateInitialMemberWidgetsEvent.from(savedMember.getId()));
        registerEvent(CreateHistoriesEvent.from(savedMember));

        return savedMember.getId();
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

    public Member findMemberById(Long id) {
        return memberRepo.findById(id)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND, id));
    }

    public MemberResponse loadMemberById(Long id) {
        return memberQueryRepo.findMemberById(id)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND, id));
    }
}
