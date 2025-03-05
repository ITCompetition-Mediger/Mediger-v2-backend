package net.mediger.user.domain.member;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import net.mediger.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class HealthConditionsTest {

    @DisplayName("건강 상태를 입력하지 않은 경우 빈 배열이 반환된다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnEmptyList_WhenHealthConditionIsNullOrEmpty(List<String> healthConditions) {
        List<HealthConditions> healthConditionList = HealthConditions.of(healthConditions);

        assertTrue(healthConditionList.isEmpty());
    }

    static Stream<Arguments> provideValidHealthConditions() {
        return Stream.of(
                Arguments.of(List.of("ESSENTIAL", "ENERGY", "STRONG_IMMUNITY")),
                Arguments.of(List.of("HAIR_GROWTH", "GOOD_SLEEP")),
                Arguments.of(List.of("DIET", "SKIN_CARE", "FOCUS", "EXAMINEE"))
        );
    }

    @DisplayName("올바른 건강 상태를 입력한 경우 HealthConditions 객체가 잘 반환된다.")
    @ParameterizedTest
    @MethodSource("provideValidHealthConditions")
    void shouldDoesNotThrowException_WhenValidHealthCondition(List<String> healthConditions) {
        assertDoesNotThrow(() -> {
            List<HealthConditions> healthConditionList = HealthConditions.of(healthConditions);

            assertEquals(healthConditions.size(), healthConditionList.size());
        });
    }

    @DisplayName("건강 상태을 소문자로 입력했을 경우 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"essential", "energy", "strong_immunity"})
    void shouldDoesNotThrowException_WhenLowerHealthCondition(String healthCondition) {
        assertThatNoException().isThrownBy(() -> HealthConditions.fromString(healthCondition));
    }

    @DisplayName("올바르지 않은 건강 상태일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"DEPRESSION", "ANXIETY", "STRESS"})
    void shouldThrowException_WhenInvalidHealthCondition(String healthCondition) {
        assertThatThrownBy(() -> HealthConditions.fromString(healthCondition))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 건강 상태입니다.");
    }
}
