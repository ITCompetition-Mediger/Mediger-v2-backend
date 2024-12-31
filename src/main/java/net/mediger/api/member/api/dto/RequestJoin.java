package net.mediger.api.member.api.dto;

public record RequestJoin(
        String account,
        String password,
        String name,
        int age
) {
}
