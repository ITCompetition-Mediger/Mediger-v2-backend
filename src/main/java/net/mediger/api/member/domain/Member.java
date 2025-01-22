package net.mediger.api.member.domain;

import static net.mediger.api.member.domain.MemberRegex.ACCOUNT_REGEX;
import static net.mediger.api.member.domain.MemberRegex.EMAIL_REGEX;
import static net.mediger.api.member.domain.MemberRegex.PASSWORD_REGEX;
import static net.mediger.api.member.domain.MemberRegex.PHONE_REGEX;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

    @Column
    private Integer age;

    @Embedded
    private HealthInfo healthInfo;

    @Embedded
    private Business business;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String account, String password, String name, String email, String phone, Role role) {
        validate(account, password, name, email, phone);
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

    public static Member createBusinessMember(String account, String password, String name, String email,
                                              String phone) {
        return Member.builder()
                .account(account)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .role(Role.BUSINESS)
                .build();
    }

    public void updateDetails(Gender gender, int age, HealthInfo healthInfo) {
        authorizedMember(role);
        this.gender = gender;
        this.age = age;
        this.healthInfo = healthInfo;
    }

    public void updateBusinessDetails(Business business) {
        authorizedBusiness(role);
        this.business = business;
    }

    public void authorizedMember(Role role) {
        if (role != Role.MEMBER) {
            throw new IllegalArgumentException("멤버 회원만 접근 가능합니다.");
        }
    }

    public void authorizedBusiness(Role role) {
        if (role != Role.BUSINESS) {
            throw new IllegalArgumentException("사업자 회원만 접근 가능합니다.");
        }
    }

    public void validate(String account, String password, String name, String email, String phone) {
        validateAccount(account);
        validatePassword(password);
        validateName(name);
        validateEmail(email);
        validatePhone(phone);
    }

    private void validateAccount(String account) {
        if (account == null || account.isBlank()) {
            throw new IllegalArgumentException("아이디는 필수로 입력해야 합니다.");
        }
        if (!ACCOUNT_REGEX.matcher(account).matches()) {
            throw new IllegalArgumentException("아이디의 형식이 올바르지 않습니다. (영문 8자 이상)");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수로 입력해야 합니다.");
        }
        if (!PASSWORD_REGEX.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호의 형식이 올바르지 않습니다. (대/소문자와 특수문자 포함 8자 이상)");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수로 입력해야 합니다.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수로 입력해야 합니다.");
        }
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 형식(xxx@xxx.xx)이 올바르지 않습니다.");
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("휴대폰 번호는 필수로 입력해야 합니다.");
        }
        if (!PHONE_REGEX.matcher(phone).matches()) {
            throw new IllegalArgumentException("휴대폰 형식이 올바르지 않습니다. (하이픈 없이)");
        }
    }
}
