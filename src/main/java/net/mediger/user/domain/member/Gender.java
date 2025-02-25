package net.mediger.user.domain.member;

import java.util.Arrays;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

public enum Gender {

    MALE, FEMALE, ETC, UNSELECTED;

    public static Gender of(String gender) {
        if (gender == null || gender.isEmpty()) {
            return UNSELECTED;
        }

        return Arrays.stream(Gender.values())
                .filter(gen -> gen.toString().equals(gender.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GENDER));
    }
}
