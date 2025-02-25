package net.mediger.user.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import net.mediger.global.exception.CustomException;
import net.mediger.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    private String account;
    private String password;
    private String name;
    private String email;
    private String phone;

    @BeforeEach
    void setUp() {
        account = "testtest";
        password = "Test123@";
        name = "테스트1";
        email = "test@email.com";
        phone = "01012345678";
    }

    @Test
    @DisplayName("정상적으로 Member 객체가 생성된다.")
    void shouldBuild_WhenValidMember() {
        Member member = Member.createMember(account, password, name, email, phone);

        assertAll(
                () -> assertEquals(account, member.getAccount()),
                () -> assertEquals(password, member.getPassword()),
                () -> assertEquals(name, member.getName()),
                () -> assertEquals(email, member.getEmail()),
                () -> assertEquals(phone, member.getPhone()),
                () -> assertEquals(Role.MEMBER, member.getRole())
        );
    }

    @Test
    @DisplayName("정상적으로 Member 추가 정보가 업데이트된다.")
    void shouldUpdate_WhenMemberDetails() {
        Member member = Member.createMember(account, password, name, email, phone);

        member.updateDetails(Gender.MALE, AgeRange.TEENS_TWENTIES,
                List.of(HealthConditions.ESSENTIAL, HealthConditions.STRONG_IMMUNITY));

        assertAll(
                () -> assertEquals(Gender.MALE, member.getGender()),
                () -> assertEquals(AgeRange.TEENS_TWENTIES, member.getAge()),
                () -> assertThat(member.getHealthConditions())
                        .containsExactlyInAnyOrder(HealthConditions.ESSENTIAL, HealthConditions.STRONG_IMMUNITY)
        );
    }

    @Nested
    @DisplayName("Member 검증 테스트")
    class ValidateMemberTest {

        @Nested
        @DisplayName("아이디 검증 테스트")
        class ValidateAccountTest {

            @ParameterizedTest
            @DisplayName("아이디가 null이거나 빈 문자열일 경우 예외가 발생한다")
            @NullAndEmptySource
            void shouldThrowException_WhenAccountNullOrEmpty(String account) {
                assertThatThrownBy(() -> Member.createMember(account, password, name, email, phone))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("아이디는 필수로 입력해야 합니다.");
            }

            @ParameterizedTest
            @DisplayName("아이디가 형식에 맞지 않을 경우 예외가 발생한다")
            @ValueSource(strings = {"test", "test123"})
            void shouldThrowException_WhenAccountInvalid(String account) {
                assertThatThrownBy(() -> Member.createMember(account, password, name, email, phone))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("아이디의 형식이 올바르지 않습니다. (영문 8자 이상)");
            }
        }

        @Nested
        @DisplayName("비밀번호 검증 테스트")
        class ValidatePasswordTest {

            @ParameterizedTest
            @DisplayName("비밀번호가 null이거나 빈 문자열일 경우 예외가 발생한다")
            @NullAndEmptySource
            void shouldThrowException_WhenPasswordNullOrEmpty(String password) {
                assertThatThrownBy(() -> Member.createMember(account, password, name, email, phone))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("비밀번호는 필수로 입력해야 합니다.");
            }
        }

        @Nested
        @DisplayName("이메일 검증 테스트")
        class ValidateEmailTest {

            @ParameterizedTest
            @DisplayName("이메일이 null이거나 빈 문자열일 경우 예외가 발생한다")
            @NullAndEmptySource
            void shouldThrowException_WhenEmailNullOrEmpty(String email) {
                assertThatThrownBy(() -> Member.createMember(account, password, name, email, phone))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("이메일은 필수로 입력해야 합니다.");
            }

            @ParameterizedTest
            @DisplayName("이메일이 형식에 맞지 않을 경우 예외가 발생한다")
            @ValueSource(strings = {"test", "test123", "test.com"})
            void shouldThrowException_WhenEmailInvalid(String email) {
                assertThatThrownBy(() -> Member.createMember(account, password, name, email, phone))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("이메일 형식(xxx@xxx.xx)이 올바르지 않습니다.");
            }
        }

        @Nested
        @DisplayName("휴대폰 번호 검증 테스트")
        class ValidatePhoneTest {

            @ParameterizedTest
            @DisplayName("휴대폰 번호가 null이거나 빈 문자열일 경우 예외가 발생한다")
            @NullAndEmptySource
            void shouldThrowException_WhenPhoneNullOrEmpty(String phone) {
                assertThatThrownBy(() -> Member.createMember(account, password, name, email, phone))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("휴대폰 번호는 필수로 입력해야 합니다.");
            }

            @ParameterizedTest
            @DisplayName("휴대폰 번호가 형식에 맞지 않을 경우 예외가 발생한다")
            @ValueSource(strings = {"010-1234-1234", "010123456789", "0101234567a"})
            void shouldThrowException_WhenPhoneInvalid(String phone) {
                assertThatThrownBy(() -> Member.createMember(account, password, name, email, phone))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("휴대폰 번호의 형식(010xxxxxxxx)이 올바르지 않습니다.");
            }
        }
    }
}
