package net.mediger.user.domain.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;

import net.mediger.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class AgeRangeTest {

    @DisplayName("나이대를 입력하지 않은 경우 기본값이 반환된다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnDefaultValue_WhenAgeIsNullOrEmpty(String ageRange) {
        AgeRange basicValue = AgeRange.of(ageRange);

        assertSame(AgeRange.UNSELECTED, basicValue);
    }

    @DisplayName("올바른 나이대를 입력한 경우 AgeRange 객체가 잘 반환된다.")
    @ParameterizedTest
    @ValueSource(strings = {"TEENS_TWENTIES", "THIRTIES", "SIXTIES_PLUS"})
    void shouldDoesNotThrowException_WhenValidAgeRange(String ageRange) {
        assertDoesNotThrow(() -> {
            AgeRange ageRange1 = AgeRange.of(ageRange);
            AgeRange ageRange2 = AgeRange.valueOf(ageRange);

            assertSame(ageRange1, ageRange2);
        });
    }

    @DisplayName("나이대를 소문자로 입력했을 경우 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"teens_twenties", "thirties", "sixties_plus"})
    void shouldDoesNotThrowException_WhenLowerAgeRange(String ageRange) {
        assertThatNoException().isThrownBy(() -> AgeRange.of(ageRange));
    }

    @DisplayName("올바르지 않은 나이대일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"10대", "20대", "30대"})
    void shouldThrowException_WhenInvalidAgeRange(String ageRange) {
        assertThatThrownBy(() -> AgeRange.of(ageRange))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 연령대입니다.");
    }
}
