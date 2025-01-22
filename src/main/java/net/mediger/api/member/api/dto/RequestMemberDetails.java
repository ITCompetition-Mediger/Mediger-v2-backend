package net.mediger.api.member.api.dto;

import net.mediger.api.member.domain.HealthInfo;

public record RequestMemberDetails(
        String gender,
        int age,
        String healthConcerns,
        String healthFocus,
        String healthChronicDisease,
        String healthInterestedDisease
) {

    public HealthInfo toHealthDetails() {
        return new HealthInfo(healthConcerns, healthFocus, healthChronicDisease, healthInterestedDisease);
    }
}
