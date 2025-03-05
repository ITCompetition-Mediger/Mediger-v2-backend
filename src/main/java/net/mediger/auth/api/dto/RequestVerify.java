package net.mediger.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RequestVerify(

        @Schema(description = "휴대폰/이메일 입력")
        String identifier,

        @Schema(description = "인증 코드 입력")
        String code
) {
}
