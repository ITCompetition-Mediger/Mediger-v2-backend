package net.mediger.user.repository;

import java.util.Optional;
import net.mediger.user.domain.business.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    boolean existsByAccount(String account);
    Optional<Business> findBusinessByAccount(String account);
}
