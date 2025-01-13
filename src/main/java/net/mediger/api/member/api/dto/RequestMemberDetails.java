package net.mediger.api.member.api.dto;

import net.mediger.api.member.domain.Gender;

public record RequestMemberDetails(
        Gender gender,
        int age,
        String healthConcerns,
        String healthFocus,
        String healthChronicDisease,
        String healthInterestedDisease
) {
}
