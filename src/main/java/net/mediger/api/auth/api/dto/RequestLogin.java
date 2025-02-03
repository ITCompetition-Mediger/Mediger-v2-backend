package net.mediger.api.auth.api.dto;

public record RequestLogin(
        String account,
        String password
) {
}
