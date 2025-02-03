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

    public RequestDetails {
        validateAge(age);
    }

    public HealthInfo toHealthDetails() {
        return new HealthInfo(healthConcerns, healthFocus, healthChronicDisease, healthInterestedDisease);
    }

    private void validateAge(int age) {
        if (age < 10) {
            throw new IllegalArgumentException("나이는 10세 이상이어야 합니다.");
        }
    }
}
