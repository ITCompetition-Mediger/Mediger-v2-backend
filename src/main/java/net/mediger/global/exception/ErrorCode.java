package net.mediger.global.exception;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NULL_ACCOUNT(HttpStatus.BAD_REQUEST, "VA001", "아이디는 필수로 입력해야 합니다."),
    EXIST_ACCOUNT(HttpStatus.BAD_REQUEST, "VA002", "이미 존재하는 아이디입니다."),
    INVALID_ACCOUNT(HttpStatus.BAD_REQUEST, "VA003", "아이디의 형식이 올바르지 않습니다. (영문 8자 이상)"),

    NULL_PASSWORD(HttpStatus.BAD_REQUEST, "VA004", "비밀번호는 필수로 입력해야 합니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "VA005", "비밀번호가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "VA006", "비밀번호의 형식이 올바르지 않습니다. (영문, 숫자, 특수문자 8자 이상)"),

    NULL_NAME(HttpStatus.BAD_REQUEST, "VA007", "이름은 필수로 입력해야 합니다."),

    NULL_EMAIL(HttpStatus.BAD_REQUEST, "VA008", "이메일은 필수로 입력해야 합니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "VA009", "이메일 형식(xxx@xxx.xx)이 올바르지 않습니다."),

    NULL_PHONE(HttpStatus.BAD_REQUEST, "VA010", "휴대폰 번호는 필수로 입력해야 합니다."),
    INVALID_PHONE(HttpStatus.BAD_REQUEST, "VA011", "휴대폰 번호의 형식(010xxxxxxxx)이 올바르지 않습니다."),

    INVALID_CERTIFICATION_CODE(HttpStatus.BAD_REQUEST, "CF001", "인증번호가 일치하지 않습니다."),

    NOT_MATCH_USER(HttpStatus.BAD_REQUEST, "AU001", "본인의 정보만 수정할 수 있습니다."),

    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "NF001", "존재하지 않는 아이디입니다."),
    NOT_FOUND_AGE_RANGE(HttpStatus.NOT_FOUND, "NF002", "존재하지 않는 연령대 입니다."),
    NOT_FOUND_HEALTH_CONDITIONS(HttpStatus.NOT_FOUND, "NF003", "존재하지 않는 건강 상태 입니다."),
    NOT_FOUND_GENDER(HttpStatus.NOT_FOUND, "NF004", "존재하지 않는 성별 입니다."),
    NOT_FOUNT_BANK(HttpStatus.NOT_FOUND, "NF005", "존재하지 않는 은행 입니다."),

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "UA001", "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "UA002", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UA003", "인증이 필요합니다."),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "FB001", "접근 권한이 없습니다."),

    FAILED_SEND_MAIL(HttpStatus.INTERNAL_SERVER_ERROR, "SE001", "이메일 전송에 실패했습니다.")
    ;

    private final HttpStatus status;
    private final String codeName;
    private final String message;

    public static ErrorCode fromMessage(String message) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.getMessage().equals(message))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 에러 메세지 : " + message));
    }
}
