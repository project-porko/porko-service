package io.porko.member.domain;

import io.porko.config.jpa.auditor.TimeMetaFields;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_MEMBER_ID", columnNames = "member_id"),
        @UniqueConstraint(name = "UK_MEMBER_EMAIL", columnNames = "email")
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeMetaFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Embedded
    private Address address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Member(
        String memberId,
        String password,
        String name,
        String email,
        String phoneNumber,
        Address address,
        Gender gender
    ) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
    }

    public static Member of(String memberId,
        String password,
        String name,
        String email,
        String phoneNumber,
        Address address,
        Gender gender
    ) {
        return new Member(memberId, password, name, email, phoneNumber, address, gender);
    }
}
