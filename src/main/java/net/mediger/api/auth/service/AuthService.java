package net.mediger.api.auth.service;

import lombok.RequiredArgsConstructor;
import net.mediger.api.auth.api.dto.RequestBusinessJoin;
import net.mediger.api.auth.api.dto.RequestJoin;
import net.mediger.api.auth.api.dto.RequestLogin;
import net.mediger.api.auth.jwt.ResponseToken;
import net.mediger.api.auth.jwt.TokenProvider;
import net.mediger.api.member.domain.Business;
import net.mediger.api.member.domain.Member;
import net.mediger.api.member.repository.BusinessRepository;
import net.mediger.api.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BusinessRepository businessRepository;
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

        Business business = Business.createBusiness(requestBusinessJoin.account(), encodedPassword,
                requestBusinessJoin.name(), requestBusinessJoin.email(), requestBusinessJoin.registrationNumber(),
                requestBusinessJoin.startDate(), requestBusinessJoin.ownerName(), requestBusinessJoin.companyName());

        businessRepository.save(business);
    }

    @Transactional
    public ResponseToken login(RequestLogin requestLogin) {
        Member member = findAccount(requestLogin.account());
        isMatchedPassword(requestLogin.password(), member);

        return tokenProvider.generateToken(member.getId(), member.getRole().name());
    }

    @Transactional
    public ResponseToken loginBusiness(RequestLogin requestLogin) {
        Business business = findBusinessAccount(requestLogin.account());
        isMatchedPasswordByBusiness(requestLogin.password(), business);

        return tokenProvider.generateToken(business.getId(), business.getRole().name());
    }

    private Member findAccount(String account) {
        return memberRepository.findMemberByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }

    private Business findBusinessAccount(String account) {
        return businessRepository.findBusinessByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기업 아이디입니다."));
    }

    private void isMatchedPassword(String password, Member member) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void isMatchedPasswordByBusiness(String password, Business business) {
        if (!passwordEncoder.matches(password, business.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
