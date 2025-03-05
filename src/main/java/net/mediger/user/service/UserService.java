package net.mediger.user.service;

import lombok.RequiredArgsConstructor;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import net.mediger.user.api.dto.RequestBusinessDetails;
import net.mediger.user.api.dto.RequestDetails;
import net.mediger.user.domain.User;
import net.mediger.user.domain.business.Bank;
import net.mediger.user.domain.member.AgeRange;
import net.mediger.user.domain.business.Business;
import net.mediger.user.domain.member.Gender;
import net.mediger.user.domain.member.HealthConditions;
import net.mediger.user.domain.member.Member;
import net.mediger.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateDetails(Long id, RequestDetails requestDetails) {
        User user = findAccount(id);

        if (user instanceof Member member) {
            member.updateDetails(Gender.of(requestDetails.gender()),
                    AgeRange.of((requestDetails.age())),
                    HealthConditions.of(requestDetails.healthConditions())
            );
        }
    }

    @Transactional
    public void updateBusinessDetails(Long id, RequestBusinessDetails requestDetails) {
        User user = findAccount(id);

        if (user instanceof Business business) {
            business.updateDetails(requestDetails.address(), requestDetails.onlineSalesRegistrationNumber(),
                    Bank.of(requestDetails.settlementBank()), requestDetails.settlementAccount(),
                    requestDetails.documents()
            );
        }
    }

    private User findAccount(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
