package net.mediger.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXIST_ACCOUNT(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    NULL_ACCOUNT(HttpStatus.BAD_REQUEST, "아이디는 필수로 입력해야 합니다."),
    INVALID_ACCOUNT(HttpStatus.BAD_REQUEST, "아이디의 형식이 올바르지 않습니다. (영문 8자 이상)"),

    NULL_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 필수로 입력해야 합니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호의 형식이 올바르지 않습니다. (영문, 숫자, 특수문자 8자 이상)"),

    NULL_NAME(HttpStatus.BAD_REQUEST, "이름은 필수로 입력해야 합니다."),

    NULL_EMAIL(HttpStatus.BAD_REQUEST, "이메일은 필수로 입력해야 합니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "이메일 형식(xxx@xxx.xx)이 올바르지 않습니다."),

    NULL_PHONE(HttpStatus.BAD_REQUEST, "휴대폰 번호는 필수로 입력해야 합니다."),
    INVALID_PHONE(HttpStatus.BAD_REQUEST, "휴대폰 번호의 형식(010xxxxxxxx)이 올바르지 않습니다."),

    NOT_MATCH_USER(HttpStatus.BAD_REQUEST, "본인의 정보만 수정할 수 있습니다."),

    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
    NOT_FOUND_AGE_RANGE(HttpStatus.NOT_FOUND, "존재하지 않는 연령대 입니다."),
    NOT_FOUND_HEALTH_CONDITIONS(HttpStatus.NOT_FOUND, "존재하지 않는 건강 상태 입니다."),
    NOT_FOUND_GENDER(HttpStatus.NOT_FOUND, "존재하지 않는 성별 입니다."),

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
