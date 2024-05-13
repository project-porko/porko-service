package io.porko.member.domain;

public class Member {
    private Long id;
    private String memberId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private Address address;
    private Gender gender;

    public Member(
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
