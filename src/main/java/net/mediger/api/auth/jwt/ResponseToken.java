package net.mediger.api.auth.jwt;

import lombok.Builder;

@Builder
public record ResponseToken(
        String accessToken
) {
}
