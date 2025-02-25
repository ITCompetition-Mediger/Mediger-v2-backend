package net.mediger.auth.api.dto;

import static net.mediger.auth.api.dto.JoinRegex.ACCOUNT_REGEX;
import static net.mediger.auth.api.dto.JoinRegex.PASSWORD_REGEX;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RequestBusinessJoin(

        @Schema(description = "아이디", example = "exampleId")
        @NotBlank
        @Pattern(regexp = ACCOUNT_REGEX, message = "아이디의 형식이 올바르지 않습니다. (영문 8자 이상)")
        String account,

        @Schema(description = "비밀번호", example = "examplePw123!")
        @NotBlank
        @Pattern(regexp = PASSWORD_REGEX, message = "비밀번호의 형식이 올바르지 않습니다. (영문, 숫자, 특수문자 8자 이상)")
        String password,

        @NotBlank(message = "이름은 필수로 입력해야 합니다.")
        String name,

        @Schema(description = "이메일", example = "example@example.com")
        @NotBlank
        @Pattern(regexp = JoinRegex.EMAIL_REGEX, message = "이메일 형식(xxx@xxx.xx)이 올바르지 않습니다.")
        String email,

        @NotBlank
        String registrationNumber,

        @NotBlank
        String startDate,

        @NotBlank
        String ownerName,

        @NotBlank
        String companyName
) {
}
