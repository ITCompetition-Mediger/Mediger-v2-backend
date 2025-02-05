package net.mediger.api.member.domain;

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
            default -> throw new IllegalArgumentException("존재하지 않는 연령대입니다.");
        };
    }
}
