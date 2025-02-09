package net.mediger.auth.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.mediger.auth.jwt.redis.RedisService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationCodeService {

    private final RedisService redisService;

    public String generateCertificationCode(String identifier) {
//        String code = String.format("%04d", new Random().nextInt(1000000));
        String code = "0000";
        redisService.saveCertificationCode(identifier, code);
        return code;
    }

    public String getCertificationCode(String identifier) {
        return redisService.getCertificationCode(identifier);
    }

    public void deleteCertificationCode(String identifier) {
        redisService.deleteCertificationCode(identifier);
    }
}
