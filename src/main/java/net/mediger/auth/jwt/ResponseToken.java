package net.mediger.auth.jwt;

import lombok.Builder;

@Builder
public record ResponseToken(
        String accessToken
) {
}
