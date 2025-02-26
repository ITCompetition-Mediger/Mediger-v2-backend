package net.mediger.user.domain.member;

import static net.mediger.global.exception.ErrorCode.NOT_FOUND_HEALTH_CONDITIONS;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import net.mediger.global.exception.CustomException;

public enum HealthConditions {

    ESSENTIAL("필수 건강"),
    ENERGY("에너지"),
    STRONG_IMMUNITY("면역 튼튼"),
    HAIR_GROWTH("모발 쑥쑥"),
    GOOD_SLEEP("꿀잠"),
    MENSTRUAL_DISCOMFORT("월경 전 불편"),
    BONE_DIAGNOSIS("뼈 진단"),
    KIDNEY("애주가"),
    BLOOD_VESSELS("혈관 튼튼"),
    BLOOD_SUGAR("혈당 관리"),
    DIGESTION("편한 소화"),
    DIET("다이어트"),
    SKIN_CARE("피부 미용"),
    FOCUS("집중"),
    EXAMINEE("수험생 건강");

    private final String healthCondition;

    HealthConditions(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public static HealthConditions fromString(String condition) {
        return Stream.of(values())
                .filter(c -> c.name().equalsIgnoreCase(condition))
                .findFirst()
                .orElseThrow(() -> new CustomException(NOT_FOUND_HEALTH_CONDITIONS));
    }

     public static List<HealthConditions> of(List<String> healthCondition) {
        if (healthCondition == null || healthCondition.isEmpty()) {
            return Collections.emptyList();
        }

        return healthCondition.stream()
                .map(HealthConditions::fromString)
                .toList();
    }
}
