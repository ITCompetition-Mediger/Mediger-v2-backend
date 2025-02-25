package net.mediger.user.domain.member;

import java.util.Arrays;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

public enum AgeRange {

    TEENS_TWENTIES("~20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES("50대"),
    SIXTIES_PLUS("60대~"),
    UNSELECTED("미선택");

    private final String age;

    AgeRange(String age) {
        this.age = age;
    }

    public static AgeRange of(String age) {
        if (age == null || age.isEmpty()) {
            return UNSELECTED;
        }

        return Arrays.stream(AgeRange.values())
                .filter(ageRange -> ageRange.toString().equals(age.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_AGE_RANGE));
    }
}
