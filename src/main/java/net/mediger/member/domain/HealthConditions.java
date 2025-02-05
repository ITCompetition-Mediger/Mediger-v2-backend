package net.mediger.member.domain;

import java.util.List;

public enum HealthConditions {

    ESSENTIAL("필수건강"),
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
                case "면역 튼튼" -> STRONG_IMMUNITY;
                case "모발 쑥쑥" -> HAIR_GROWTH;
                case "꿀잠" -> GOOD_SLEEP;
                case "월경 전 불편" -> MENSTRUAL_DISCOMFORT;
                case "뼈 진단" -> BONE_DIAGNOSIS;
                case "애주가" -> KIDNEY;
                case "혈관 튼튼" -> BLOOD_VESSELS;
                case "혈당 관리" -> BLOOD_SUGAR;
                case "편한 소화" -> DIGESTION;
                case "다이어트" -> DIET;
                case "피부 미용" -> SKIN_CARE;
                case "집중" -> FOCUS;
                case "수험생 건강" -> EXAMINEE;
                default -> throw new IllegalArgumentException("존재하지 않는 건강조건입니다.");
            };
        }
        return null;
    }
}
