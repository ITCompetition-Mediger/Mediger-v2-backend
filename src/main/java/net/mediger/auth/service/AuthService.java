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
import net.mediger.user.domain.User;
import net.mediger.user.domain.business.Business;
import net.mediger.user.domain.member.Member;
import net.mediger.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CertificationCodeService certificationCodeService;
    private final SMSMessageSender smsMessageSender;
    private final MailMessageSender mailMessageSender;

    public boolean isCheckedAccount(String account) {
        if (userRepository.existsByAccount(account)) {
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

        userRepository.save(member);

        return login(new RequestLogin(requestJoin.account(), requestJoin.password()));
    }

    @Transactional
    public ResponseToken joinBusiness(RequestBusinessJoin requestBusinessJoin) {
        isCheckedAccount(requestBusinessJoin.account());
        String encodedPassword = passwordEncoder.encode(requestBusinessJoin.password());

        Business business = Business.createBusiness(requestBusinessJoin.account(), encodedPassword,
                requestBusinessJoin.name(), requestBusinessJoin.email(), requestBusinessJoin.registrationNumber(),
                requestBusinessJoin.startDate(), requestBusinessJoin.ownerName(), requestBusinessJoin.companyName());

        userRepository.save(business);

        return login(new RequestLogin(requestBusinessJoin.account(), requestBusinessJoin.password()));
    }

    @Transactional(readOnly = true)
    public ResponseToken login(RequestLogin requestLogin) {
        User user = findAccount(requestLogin.account());
        isMatchedPassword(requestLogin.password(), user);

        return tokenProvider.generateToken(user.getId(), user.getRole().name());
    }

    private User findAccount(String account) {
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    private void isMatchedPassword(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }
    }
}
