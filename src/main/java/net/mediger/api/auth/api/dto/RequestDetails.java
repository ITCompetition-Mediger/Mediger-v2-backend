package net.mediger.api.auth.api.dto;

import net.mediger.api.member.domain.HealthInfo;

public record RequestDetails(
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
