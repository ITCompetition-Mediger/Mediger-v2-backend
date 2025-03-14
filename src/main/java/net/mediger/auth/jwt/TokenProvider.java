package net.mediger.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import net.mediger.global.properties.JwtProperties;
import net.mediger.auth.jwt.redis.RedisService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class TokenProvider {

    private static final String ID_KEY = "id";
    private static final String ROLE_KEY = "role";
    private static final String ROLE_PREFIX = "ROLE_";

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
                .claim(ROLE_KEY, ROLE_PREFIX + role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.accessTokenExpiration()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.secret().getBytes()))
                .compact();
    }

    public Long getIdFromToken(String token) {
        return Long.parseLong(getClaimFromToken(token, ID_KEY));
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
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
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
