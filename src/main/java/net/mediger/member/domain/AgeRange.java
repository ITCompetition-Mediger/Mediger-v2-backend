package net.mediger.member.domain;

import lombok.Getter;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

@Getter
public enum AgeRange {

    TEENAGER(10),
    TWENTIES(20),
    THIRTIES(30),
    FORTIES(40),
    FIFTIES(50),
    SIXTIES(60),
    SEVENTIES(70),
    EIGHTIES(80),
    NINETIES(90),
    CENTENARIAN(100);

    private final int age;

    AgeRange(int age) {
        this.age = age;
    }

    public static AgeRange findAgeRange(int age) {
        return switch (age) {
            case 10 -> TEENAGER;
            case 20 -> TWENTIES;
            case 30 -> THIRTIES;
            case 40 -> FORTIES;
            case 50 -> FIFTIES;
            case 60 -> SIXTIES;
            case 70 -> SEVENTIES;
            case 80 -> EIGHTIES;
            case 90 -> NINETIES;
            case 100 -> CENTENARIAN;
            default -> throw new CustomException(ErrorCode.NOT_FOUND_AGE_RANGE);
        };
    }
}
