package net.mediger.api.auth.api.dto;

public record RequestJoin(
        String account,
        String password,
        String name,
        String email,
        String phone
) {
}
