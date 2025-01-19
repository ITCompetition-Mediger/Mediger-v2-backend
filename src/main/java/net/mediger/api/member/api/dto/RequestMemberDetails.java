package net.mediger.api.member.api.dto;

public record RequestMemberDetails(
        String gender,
        int age,
        String healthConcerns,
        String healthFocus,
        String healthChronicDisease,
        String healthInterestedDisease
) {
}
