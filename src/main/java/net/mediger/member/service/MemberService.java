package net.mediger.member.service;

import lombok.RequiredArgsConstructor;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import net.mediger.member.api.dto.RequestBusinessDetails;
import net.mediger.member.api.dto.RequestDetails;
import net.mediger.member.domain.AgeRange;
import net.mediger.member.domain.Business;
import net.mediger.member.domain.Gender;
import net.mediger.member.domain.HealthConditions;
import net.mediger.member.domain.Member;
import net.mediger.member.repository.BusinessRepository;
import net.mediger.member.repository.MemberRepository;
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
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    private Business findBusinessAccount(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }
}
