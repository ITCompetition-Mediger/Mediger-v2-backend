package net.mediger.user.domain.business;

import java.util.Arrays;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;

public enum Bank {
    KB_KOOKMIN("KB국민은행"),
    SHINHAN("신한은행"),
    WOOREE("우리은행"),
    KEB_HANA("KEB하나은행"),
    SC_FIRST_BANK("SC제일은행"),
    CITIBANK("씨티은행"),
    SH_SUHYEOP("Sh수협은행"),
    NH_NONGHYUP("NH농협은행"),
    KAKAO_BANK("카카오뱅크"),
    K_BANK("케이뱅크"),
    TOSS_BANK("토스뱅크"),
    BANK_OF_KOREA("한국은행"),
    EXIM_BANK("수출입은행"),
    KDB_BANK("KDB산업은행"),
    IBK_CORPORATE("IBK기업은행"),
    UNSELECTED("선택안함");

    private final String name;

    Bank(String name) {
        this.name = name;
    }

    public static Bank of(String name) {
        if (name == null || name.isEmpty()) {
            return UNSELECTED;
        }

        return Arrays.stream(Bank.values())
                .filter(bank -> bank.toString().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_BANK));
    }
}
