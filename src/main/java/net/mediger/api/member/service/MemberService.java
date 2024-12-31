package net.mediger.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mediger.api.member.api.dto.RequestJoin;
import net.mediger.api.member.domain.Member;
import net.mediger.api.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(RequestJoin joinMember) {
        String encodedPassword = passwordEncoder.encode(joinMember.password());
        Member newMember = createMember(joinMember, encodedPassword);
        memberRepository.save(newMember);
    }

    private Member createMember(RequestJoin joinMember, String encodedPassword) {
        return Member.builder()
                .account(joinMember.account())
                .password(encodedPassword)
                .name(joinMember.name())
                .age(joinMember.age())
                .build();
    }
}
