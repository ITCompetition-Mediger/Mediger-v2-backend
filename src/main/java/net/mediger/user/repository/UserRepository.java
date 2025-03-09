package net.mediger.user.repository;

import java.util.Optional;
import net.mediger.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccount(String account);
    boolean existsByEmail(String email);
    Optional<User> findByAccount(String account);
}
