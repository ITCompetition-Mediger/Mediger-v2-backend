package net.mediger.api.member.service;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import net.mediger.api.member.api.dto.RequestBusinessDetails;
import net.mediger.api.member.api.dto.RequestJoin;
import net.mediger.api.member.api.dto.RequestMemberDetails;
import net.mediger.api.member.domain.Business;
import net.mediger.api.member.domain.Gender;
import net.mediger.api.member.domain.HealthInfo;
import net.mediger.api.member.domain.Member;
import net.mediger.api.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

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
    public void join(RequestJoin joinMember) {
        String encodedPassword = passwordEncoder.encode(joinMember.password());
        checkedEmail(joinMember.email());

        Member newMember = Member.createMember(joinMember.account(), encodedPassword, joinMember.name(),
                joinMember.email(), joinMember.phone());

        memberRepository.save(newMember);
    }

    private void checkedEmail(String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    @Transactional
    public void joinBusiness(RequestJoin joinMember) {
        String encodedPassword = passwordEncoder.encode(joinMember.password());
        Member newMember = Member.createBusinessMember(joinMember.account(), encodedPassword, joinMember.name(),
                joinMember.email(), joinMember.phone());

        memberRepository.save(newMember);
    }

    @Transactional
    public void updateDetails(Long id, RequestMemberDetails requestDetails) {
        Member member = findMember(id);
        HealthInfo healthInfo = getHealthInfo(requestDetails);

        member.updateDetails(
                Gender.findGender(requestDetails.gender()),
                requestDetails.age(),
                healthInfo
        );
    }

    @Transactional
    public void updateBusinessDetails(Long id, RequestBusinessDetails requestDetails) {
        Member member = findMember(id);
        Business business = getBusiness(requestDetails);

        member.updateBusinessDetails(business);
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    private HealthInfo getHealthInfo(RequestMemberDetails requestDetails) {
        return new HealthInfo(
                requestDetails.healthConcerns(),
                requestDetails.healthFocus(),
                requestDetails.healthChronicDisease(),
                requestDetails.healthInterestedDisease()
        );
    }

    private Business getBusiness(RequestBusinessDetails requestDetails) {
        return new Business(
                requestDetails.name(),
                requestDetails.registrationNumber(),
                requestDetails.address(),
                requestDetails.ownerName(),
                requestDetails.openingDate(),
                requestDetails.ecommerceRegistrationNumber(),
                requestDetails.settlementAccount(),
                requestDetails.documents()
        );
    }
}
