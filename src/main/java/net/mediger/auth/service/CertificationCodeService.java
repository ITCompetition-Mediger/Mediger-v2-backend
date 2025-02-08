package net.mediger.auth.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.mediger.auth.jwt.redis.RedisService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationCodeService {

    private final RedisService redisService;

    public String generateCertificationCode(String phone) {
//        String code = String.format("%04d", new Random().nextInt(1000000));
        String code = "0000";
        redisService.saveCertificationCode(phone, code);
        return code;
    }

    public String getCertificationCode(String phone) {
        return redisService.getCertificationCode(phone);
    }

    public void deleteCertificationCode(String phone) {
        redisService.deleteCertificationCode(phone);
    }
}
