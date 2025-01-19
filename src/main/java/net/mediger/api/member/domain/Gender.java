package net.mediger.api.member.domain;

import java.util.Arrays;

public enum Gender {

    MALE, FEMALE, ETC;

    public static Gender findGender(String gender) {
        return Arrays.stream(Gender.values())
                .filter(gen -> gen.toString().equals(gender.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 성별입니다."));
    }
}
