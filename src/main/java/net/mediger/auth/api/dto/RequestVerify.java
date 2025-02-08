package net.mediger.auth.api.dto;

public record RequestVerify(
        String phone,
        String code
) {
}
