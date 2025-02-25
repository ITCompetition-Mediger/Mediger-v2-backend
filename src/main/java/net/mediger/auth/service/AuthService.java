package net.mediger.auth.service;

import lombok.RequiredArgsConstructor;
import net.mediger.auth.api.dto.RequestBusinessJoin;
import net.mediger.auth.api.dto.RequestJoin;
import net.mediger.auth.api.dto.RequestLogin;
import net.mediger.auth.jwt.ResponseToken;
import net.mediger.auth.jwt.TokenProvider;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import net.mediger.global.message.MailMessageSender;
import net.mediger.global.message.SMSMessageSender;
import net.mediger.user.domain.business.Business;
import net.mediger.user.domain.member.Member;
import net.mediger.user.repository.BusinessRepository;
import net.mediger.user.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final BusinessRepository businessRepository;
    private final CertificationCodeService certificationCodeService;
    private final SMSMessageSender smsMessageSender;
    private final MailMessageSender mailMessageSender;

    @Transactional
    public boolean isCheckedAccount(String account) {
        if (memberRepository.existsByAccount(account)) {
            throw new CustomException(ErrorCode.EXIST_ACCOUNT);
        }

        if (businessRepository.existsByAccount(account)) {
            throw new CustomException(ErrorCode.EXIST_ACCOUNT);
        }

        return true;
    }

    public void certification(String phone) {
        String code = certificationCodeService.generateCertificationCode(phone);
        smsMessageSender.send(phone, code);
    }

    public void certificationBusiness(String email) {
        String code = certificationCodeService.generateCertificationCode(email);
        mailMessageSender.send(email, code);
    }

    public boolean verify(String identifier, String code) {
        String savedCode = certificationCodeService.getCertificationCode(identifier);

        if (!ObjectUtils.nullSafeEquals(savedCode, code)) {
            throw new CustomException(ErrorCode.INVALID_CERTIFICATION_CODE);
        }

        certificationCodeService.deleteCertificationCode(identifier);
        return true;
    }

    @Transactional
    public ResponseToken join(RequestJoin requestJoin) {
        isCheckedAccount(requestJoin.account());
        String encodedPassword = passwordEncoder.encode(requestJoin.password());

        Member member = Member.createMember(requestJoin.account(), encodedPassword, requestJoin.name(),
                requestJoin.email(), requestJoin.phone());

        memberRepository.save(member);

        return login(new RequestLogin(requestJoin.account(), requestJoin.password()));
    }

    @Transactional
    public ResponseToken joinBusiness(RequestBusinessJoin requestBusinessJoin) {
        isCheckedAccount(requestBusinessJoin.account());
        String encodedPassword = passwordEncoder.encode(requestBusinessJoin.password());

        Business business = Business.createBusiness(requestBusinessJoin.account(), encodedPassword,
                requestBusinessJoin.name(), requestBusinessJoin.email(), requestBusinessJoin.registrationNumber(),
                requestBusinessJoin.startDate(), requestBusinessJoin.ownerName(), requestBusinessJoin.companyName());

        businessRepository.save(business);

        return loginBusiness(new RequestLogin(requestBusinessJoin.account(), requestBusinessJoin.password()));
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
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    private Business findBusinessAccount(String account) {
        return businessRepository.findBusinessByAccount(account)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    private void isMatchedPassword(String password, Member member) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }
    }

    private void isMatchedPasswordByBusiness(String password, Business business) {
        if (!passwordEncoder.matches(password, business.getPassword())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }
    }
}
