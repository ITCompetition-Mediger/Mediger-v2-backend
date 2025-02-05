package net.mediger.api.auth.api.dto;

import static net.mediger.api.auth.api.dto.JoinRegex.ACCOUNT_REGEX;
import static net.mediger.api.auth.api.dto.JoinRegex.EMAIL_REGEX;
import static net.mediger.api.auth.api.dto.JoinRegex.PASSWORD_REGEX;

import java.time.LocalDate;

public record RequestBusinessJoin(
        String account,
        String password,
        String name,
        String email,
        String registrationNumber,
        LocalDate startDate,
        String ownerName,
        String companyName
) {

    public RequestBusinessJoin {
        validate(account, password, name, email);
    }

    public void validate(String account, String password, String name, String email) {
        validateAccount(account);
        validatePassword(password);
        validateName(name);
        validateEmail(email);
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
}
