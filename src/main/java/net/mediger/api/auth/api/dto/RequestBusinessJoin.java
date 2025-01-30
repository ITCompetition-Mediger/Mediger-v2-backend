package net.mediger.api.auth.api.dto;

public record RequestBusinessJoin(
        String account,
        String password,
        String email,
        String phone,
        String name,
        String business_name,
        String registrationNumber
) {
}
