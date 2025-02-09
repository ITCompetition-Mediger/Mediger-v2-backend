package net.mediger.auth.jwt.redis;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String CERTIFICATION_PREFIX = "certification:";

    private final RedisRepository redisRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String tokenId, Long memberId) {
        try {
            RefreshToken refreshToken = new RefreshToken(tokenId, memberId);
            deleteRefreshToken(tokenId);
            redisRepository.save(refreshToken);
            log.info("\uD83D\uDD34 RefreshToken 저장 완료: {}", refreshToken);
        } catch (Exception e) {
            log.error("\uD83D\uDD34 RefreshToken 저장 실패: {}", e.getMessage());
        }
    }

    public void deleteRefreshToken(String tokenId) {
        redisRepository.deleteById(tokenId);
    }

    public void saveCertificationCode(String identifier, String code) {
        try {
            String key = CERTIFICATION_PREFIX + identifier;
            redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(3));
            log.info("\uD83D\uDFE2 인증번호 저장 완료: {}", key);
        } catch (Exception e) {
            log.error("\uD83D\uDFE2 인증번호 저장 실패: {}", e.getMessage());
        }
    }

    public String getCertificationCode(String identifier) {
        String key = CERTIFICATION_PREFIX + identifier;
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteCertificationCode(String identifier) {
        String key = CERTIFICATION_PREFIX + identifier;
        redisTemplate.delete(key);
    }
}
