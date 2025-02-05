package net.mediger.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mediger.api.member.api.dto.RequestBusinessDetails;
import net.mediger.api.member.api.dto.RequestDetails;
import net.mediger.api.member.domain.AgeRange;
import net.mediger.api.member.domain.Business;
import net.mediger.api.member.domain.Gender;
import net.mediger.api.member.domain.HealthConditions;
import net.mediger.api.member.domain.Member;
import net.mediger.api.member.repository.BusinessRepository;
import net.mediger.api.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    public void updateDetails(Long id, RequestDetails requestDetails) {
        Member member = findAccount(id);
        member.updateDetails(
                Gender.findGender(requestDetails.gender()),
                AgeRange.findAgeRange(requestDetails.age()),
                HealthConditions.of(requestDetails.healthCondition())
        );
    }

    @Transactional
    public void updateBusinessDetails(Long id, RequestBusinessDetails requestDetails) {
        Business business = findBusinessAccount(id);
        business.updateBusinessDetails(requestDetails.address(), requestDetails.ecommerceRegistrationNumber(),
                requestDetails.settlementAccount(), requestDetails.settlementAccount());
    }

    private Member findAccount(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }

    private Business findBusinessAccount(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기업 아이디입니다."));
    }
}
