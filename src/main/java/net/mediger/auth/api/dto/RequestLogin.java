package net.mediger.auth.api.dto;

public record RequestLogin(
        String account,
        String password
) {
}
