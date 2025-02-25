package net.mediger.user.service;

import lombok.RequiredArgsConstructor;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import net.mediger.user.api.dto.RequestBusinessDetails;
import net.mediger.user.api.dto.RequestDetails;
import net.mediger.user.domain.business.Bank;
import net.mediger.user.domain.member.AgeRange;
import net.mediger.user.domain.business.Business;
import net.mediger.user.domain.member.Gender;
import net.mediger.user.domain.member.HealthConditions;
import net.mediger.user.domain.member.Member;
import net.mediger.user.repository.BusinessRepository;
import net.mediger.user.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    public void updateDetails(Long id, RequestDetails requestDetails) {
        Member member = findAccount(id);

        member.updateDetails(
                Gender.of(requestDetails.gender()),
                AgeRange.of(requestDetails.age()),
                HealthConditions.of(requestDetails.healthConditions())
        );
    }

    @Transactional
    public void updateBusinessDetails(Long id, RequestBusinessDetails requestDetails) {
        Business business = findBusinessAccount(id);

        business.updateDetails(requestDetails.address(), requestDetails.onlineSalesRegistrationNumber(),
                Bank.of(requestDetails.settlementBank()), requestDetails.settlementAccount(),
                requestDetails.documents());
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
