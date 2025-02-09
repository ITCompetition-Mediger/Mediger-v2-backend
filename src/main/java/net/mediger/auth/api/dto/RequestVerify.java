package net.mediger.auth.api.dto;

public record RequestVerify(
        String identifier,
        String code
) {
}
