package net.mediger.user.domain;

import static net.mediger.auth.api.dto.JoinRegex.ACCOUNT_REGEX;
import static net.mediger.auth.api.dto.JoinRegex.EMAIL_REGEX;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mediger.global.domain.BaseTimeEntity;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public abstract class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    protected User(String account, String password, String name, String email,
                   Role role) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        validate();
    }

    public void validate() {
        validateAccount();
        validatePassword();
        validateName();
        validateEmail();
    }

    private void validateAccount() {
        if (account == null || account.isBlank()) {
            throw new CustomException(ErrorCode.NULL_ACCOUNT);
        }
        if (!account.matches(ACCOUNT_REGEX)) {
            throw new CustomException(ErrorCode.INVALID_ACCOUNT);
        }
    }

    private void validatePassword() {
        if (password == null || password.isBlank()) {
            throw new CustomException(ErrorCode.NULL_PASSWORD);
        }
    }

    private void validateName() {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.NULL_NAME);
        }
    }

    private void validateEmail() {
        if (email == null || email.isBlank()) {
            throw new CustomException(ErrorCode.NULL_EMAIL);
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        }
    }
}
