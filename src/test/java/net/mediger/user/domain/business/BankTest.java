package net.mediger.user.domain.business;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import net.mediger.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class BankTest {

    @DisplayName("은행을 입력하지 않은 경우 기본값이 반환된다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnDefaultValue_WhenBankIsNullOrEmpty(String bank) {
        Bank basicValue = Bank.of(bank);

        assertSame(Bank.UNSELECTED, basicValue);
    }

    @DisplayName("정상적으로 Bank 객체가 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"KB_KOOKMIN", "SHINHAN", "WOOREE"})
    void shouldDoesNotThrowException_WhenValidBank(String bank) {
        assertDoesNotThrow(() -> {
            Bank bank1 = Bank.of(bank);
            Bank bank2 = Bank.valueOf(bank);

            assertSame(bank1, bank2);
        });
    }

    @DisplayName("은행을 소문자로 입력했을 경우 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"kb_kookmin", "shinhan", "wooree"})
    void shouldDoesNotThrowException_WhenLowerBank(String bank) {
        assertThatNoException().isThrownBy(() -> Bank.of(bank));
    }

    @DisplayName("올바르지 않은 은행일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"MEDI_BANK","TEMP_BANK","TEST_BANK"})
    void shouldThrowException_WhenInvalidBank(String bank) {
        assertThatThrownBy( () -> Bank.of(bank))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 은행입니다.");
    }
}
