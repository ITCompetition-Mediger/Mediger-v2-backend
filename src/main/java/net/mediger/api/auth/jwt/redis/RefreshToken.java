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
    private String account;

    public RefreshToken(String refreshToken, String account) {
        this.refreshToken = refreshToken;
        this.account = account;
    }
}
