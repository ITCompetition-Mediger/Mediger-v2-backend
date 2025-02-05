package net.mediger.member.repository;

import java.util.Optional;
import net.mediger.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByAccount(String account);
    Optional<Member> findMemberByAccount(String account);
}
