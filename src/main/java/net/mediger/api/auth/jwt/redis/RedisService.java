package net.mediger.api.auth.jwt.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisRepository redisRepository;

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
}
