package net.mediger.user.repository;

import net.mediger.user.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByPhone(String phone);
}
