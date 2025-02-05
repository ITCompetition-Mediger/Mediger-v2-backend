package net.mediger.api.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mediger.global.properties.JwtProperties;
import net.mediger.api.auth.jwt.redis.RedisService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class TokenProvider {

    private static final String ID_KEY = "id";
    private static final String ROLE_KEY = "role";

    private final JwtProperties jwtProperties;
    private final RedisService redisService;

    public ResponseToken generateToken(Long id, String role) {
        String accessToken = generateAccessToken(id, role);
        String refreshToken = UUID.randomUUID().toString();

        redisService.saveRefreshToken(refreshToken, id);

        return ResponseToken.builder()
                .accessToken(accessToken)
                .build();
    }

    public String generateAccessToken(Long id, String role) {
        Date now = new Date();

        return Jwts.builder()
                .claim(ID_KEY, id)
                .claim(ROLE_KEY, role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.accessTokenExpiration()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.secret().getBytes()))
                .compact();
    }

    public String getIdFromToken(String token) {
        return getClaimFromToken(token, ID_KEY);
    }

    public String getRoleFromToken(String token) {
        return getClaimFromToken(token, ROLE_KEY);
    }

    private String getClaimFromToken(String token, String key) {
        try {
            return decodedToken(token)
                    .get(key)
                    .toString();
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("만료된 토큰입니다.", e.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.", e.getCause());
        }
    }

    private Claims decodedToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtProperties.secret().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            decodedToken(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
