package net.mediger.member.domain;

import java.util.List;
import lombok.Getter;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

@Getter
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

    public static HealthConditions of(List<String> healthCondition) {
        for (String condition : healthCondition) {
            return switch (condition) {
                case "필수건강" -> ESSENTIAL;
                case "에너지" -> ENERGY;
                case "면역튼튼" -> STRONG_IMMUNITY;
                case "모발쑥쑥" -> HAIR_GROWTH;
                case "꿀잠" -> GOOD_SLEEP;
                case "월경전불편" -> MENSTRUAL_DISCOMFORT;
                case "뼈진단" -> BONE_DIAGNOSIS;
                case "애주가" -> KIDNEY;
                case "혈관튼튼" -> BLOOD_VESSELS;
                case "혈당관리" -> BLOOD_SUGAR;
                case "편한소화" -> DIGESTION;
                case "다이어트" -> DIET;
                case "피부미용" -> SKIN_CARE;
                case "집중" -> FOCUS;
                case "수험생건강" -> EXAMINEE;
                default -> throw new CustomException(ErrorCode.NOT_FOUND_HEALTH_CONDITIONS);
            };
        }
        return null;
    }
}
