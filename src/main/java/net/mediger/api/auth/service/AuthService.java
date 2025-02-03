package net.mediger.api.auth.service;

import lombok.RequiredArgsConstructor;
import net.mediger.api.auth.api.dto.RequestBusinessDetails;
import net.mediger.api.auth.api.dto.RequestBusinessJoin;
import net.mediger.api.auth.api.dto.RequestDetails;
import net.mediger.api.auth.api.dto.RequestJoin;
import net.mediger.api.auth.api.dto.RequestLogin;
import net.mediger.api.auth.jwt.ResponseToken;
import net.mediger.api.auth.jwt.TokenProvider;
import net.mediger.api.member.domain.Gender;
import net.mediger.api.member.domain.Member;
import net.mediger.api.member.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public boolean isCheckedAccount(String account) {
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
    public ResponseToken login(RequestLogin requestLogin) {
        Member member = findAccount(requestLogin.account());
        isMatchedPassword(requestLogin.password(), member);

        return tokenProvider.generateToken(member.getAccount(), member.getRole().name());
    }

    @Transactional
    public void updateDetails(RequestDetails requestDetails) {
        String token = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Member member = findAccount(token);

        member.updateDetails(Gender.findGender(requestDetails.gender()), requestDetails.age(),
                requestDetails.toHealthDetails());
    }

    @Transactional
    public void updateBusinessDetails(RequestBusinessDetails requestDetails) {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = findAccount(token);

        member.updateBusinessDetails(requestDetails.toDetails());
    }

    private Member findAccount(String account) {
        return memberRepository.findMemberByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }

    private void isMatchedPassword(String password, Member member) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
