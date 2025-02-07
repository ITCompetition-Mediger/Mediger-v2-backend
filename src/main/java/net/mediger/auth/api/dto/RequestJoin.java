package net.mediger.auth.api.dto;

import static net.mediger.auth.api.dto.JoinRegex.ACCOUNT_REGEX;
import static net.mediger.auth.api.dto.JoinRegex.EMAIL_REGEX;
import static net.mediger.auth.api.dto.JoinRegex.PASSWORD_REGEX;
import static net.mediger.auth.api.dto.JoinRegex.PHONE_REGEX;

import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

public record RequestJoin(
        String account,
        String password,
        String name,
        String email,
        String phone
) {

    public RequestJoin {
        validate(account, password, name, email, phone);
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
            throw new CustomException(ErrorCode.NULL_ACCOUNT);
        }
        if (!ACCOUNT_REGEX.matcher(account).matches()) {
            throw new CustomException(ErrorCode.INVALID_ACCOUNT);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new CustomException(ErrorCode.NULL_PASSWORD);
        }
        if (!PASSWORD_REGEX.matcher(password).matches()) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.NULL_NAME);
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new CustomException(ErrorCode.NULL_EMAIL);
        }
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new CustomException(ErrorCode.NULL_PHONE);
        }
        if (!PHONE_REGEX.matcher(phone).matches()) {
            throw new CustomException(ErrorCode.INVALID_PHONE);
        }
    }
}
