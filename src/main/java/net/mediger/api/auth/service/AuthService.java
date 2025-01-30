package net.mediger.api.auth.service;

import lombok.RequiredArgsConstructor;
import net.mediger.api.auth.api.dto.RequestBusinessDetails;
import net.mediger.api.auth.api.dto.RequestBusinessJoin;
import net.mediger.api.auth.api.dto.RequestDetails;
import net.mediger.api.auth.api.dto.RequestJoin;
import net.mediger.api.member.domain.Gender;
import net.mediger.api.member.domain.Member;
import net.mediger.api.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean checkedAccount(String account) {
        if (memberRepository.existsByAccount(account)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        return true;
    }

    @Transactional
    public void join(RequestJoin requestJoin) {
        String encodedPassword = passwordEncoder.encode(requestJoin.password());

        Member newMember = Member.createMember(requestJoin.account(), encodedPassword, requestJoin.name(),
                requestJoin.email(), requestJoin.phone());

        memberRepository.save(newMember);
    }

    @Transactional
    public void joinBusiness(RequestBusinessJoin requestBusinessJoin) {
        String encodedPassword = passwordEncoder.encode(requestBusinessJoin.password());

        Member newMember = Member.createBusinessMember(requestBusinessJoin.account(), encodedPassword,
                requestBusinessJoin.name(),
                requestBusinessJoin.email(), requestBusinessJoin.phone());

        memberRepository.save(newMember);
    }

    @Transactional
    public void updateDetails(Long id, RequestDetails requestDetails) {
        Member member = findMember(id);

        member.updateDetails(Gender.findGender(requestDetails.gender()), requestDetails.age(),
                requestDetails.toHealthDetails());
    }

    @Transactional
    public void updateBusinessDetails(Long id, RequestBusinessDetails requestDetails) {
        Member member = findMember(id);

        member.updateBusinessDetails(requestDetails.toDetails());
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
