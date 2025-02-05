package net.mediger.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mediger.global.domain.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private AgeRange age;

    @Enumerated(EnumType.STRING)
    private HealthConditions healthConditions;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String account, String password, String name, String email, String phone, Role role) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public static Member createMember(String account, String password, String name, String email, String phone) {
        return Member.builder()
                .account(account)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .role(Role.MEMBER)
                .build();
    }

    public void updateDetails(Gender gender, AgeRange age, HealthConditions healthConditions) {
        this.gender = gender;
        this.age = age;
        this.healthConditions = healthConditions;
    }
}
