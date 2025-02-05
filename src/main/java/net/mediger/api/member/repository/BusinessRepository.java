package net.mediger.api.member.repository;

import java.util.Optional;
import net.mediger.api.member.domain.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findBusinessByAccount(String account);
}
