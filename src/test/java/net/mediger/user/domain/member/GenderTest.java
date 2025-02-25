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

class GenderTest {

    @DisplayName("성별을 입력하지 않은 경우 기본값이 반환된다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnDefaultValue_WhenGenderIsNullOrEmpty(String gender) {
        Gender basicValue = Gender.of(gender);

        assertSame(Gender.UNSELECTED, basicValue);
    }

    @DisplayName("올바른 성별을 입력한 경우 Gender 객체가 잘 반환된다.")
    @ParameterizedTest
    @ValueSource(strings = {"MALE", "FEMALE", "ETC"})
    void shouldDoesNotThrowException_WhenValidGender(String gender) {
        assertDoesNotThrow(() -> {
            Gender gender1 = Gender.of(gender);
            Gender gender2 = Gender.valueOf(gender);

            assertSame(gender1, gender2);
        });
    }

    @DisplayName("성별을 소문자로 입력했을 경우 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"male", "female", "etc"})
    void shouldDoesNotThrowException_WhenLowerGender(String gender) {
        assertThatNoException().isThrownBy(() -> Gender.of(gender));
    }

    @DisplayName("올바르지 않은 성별일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"남성", "여성", "기타"})
    void shouldThrowException_WhenInvalidGender(String invalidGender) {
        assertThatThrownBy(() -> Gender.of(invalidGender))
                .isInstanceOf(CustomException.class);
    }
}
