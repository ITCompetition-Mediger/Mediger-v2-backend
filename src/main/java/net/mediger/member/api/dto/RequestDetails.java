package net.mediger.member.api.dto;

import java.util.List;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

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
            throw new CustomException(ErrorCode.NOT_FOUND_AGE_RANGE);
        }
    }
}
