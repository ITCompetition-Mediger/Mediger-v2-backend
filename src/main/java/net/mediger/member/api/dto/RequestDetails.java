package net.mediger.member.api.dto;

import java.util.List;

public record RequestDetails(
        String gender,
        int age,
        List<String> healthCondition
) {

    public RequestDetails {
        validateAge(age);
    }

    private void validateAge(int age) {
        if (age < 10) {
            throw new IllegalArgumentException("나이는 10세 이상이어야 합니다.");
        }
    }
}
