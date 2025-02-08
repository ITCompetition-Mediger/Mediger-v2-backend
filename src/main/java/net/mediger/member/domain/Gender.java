package net.mediger.member.domain;

import java.util.Arrays;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

public enum Gender {

    MALE, FEMALE, ETC;

    public static Gender findGender(String gender) {
        return Arrays.stream(Gender.values())
                .filter(gen -> gen.toString().equals(gender.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GENDER));
    }
}
