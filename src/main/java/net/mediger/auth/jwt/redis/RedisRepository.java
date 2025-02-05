package net.mediger.auth.jwt.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RefreshToken, String> {
}
