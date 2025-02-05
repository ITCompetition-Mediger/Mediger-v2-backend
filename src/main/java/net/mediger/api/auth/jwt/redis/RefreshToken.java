package net.mediger.api.auth.jwt.redis;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refresh_token", timeToLive = 604800)
@NoArgsConstructor
public class RefreshToken implements Serializable {

    @Id
    private String refreshToken;
    private Long memberId;

    public RefreshToken(String refreshToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }
}
