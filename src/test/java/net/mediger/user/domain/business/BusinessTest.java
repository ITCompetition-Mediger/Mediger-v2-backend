package net.mediger.user.domain.business;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import net.mediger.global.exception.CustomException;
import net.mediger.user.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class BusinessTest {

    private String account;
    private String password;
    private String name;
    private String email;
    private String registrationNumber;
    private String startDate;
    private String ownerName;
    private String companyName;

    @BeforeEach
    void setUp() {
        account = "testbusiness";
        password = "Business123!";
        name = "테스트사업자";
        email = "business@test.com";
        registrationNumber = "1234567890";
        startDate = "20210101";
        ownerName = "대표님";
        companyName = "테스트회사";
    }

    @Test
    @DisplayName("정상적으로 Business 객체가 생성된다.")
    void shouldBuild_WhenValidBusiness() {
        Business business = Business.createBusiness(account, password, name, email, registrationNumber,
                startDate, ownerName, companyName);

        assertAll(
                () -> assertEquals(account, business.getAccount()),
                () -> assertEquals(password, business.getPassword()),
                () -> assertEquals(name, business.getName()),
                () -> assertEquals(email, business.getEmail()),
                () -> assertEquals(registrationNumber, business.getRegistrationNumber()),
                () -> assertEquals(startDate, business.getStartDate()),
                () -> assertEquals(ownerName, business.getOwnerName()),
                () -> assertEquals(companyName, business.getCompanyName()),
                () -> assertEquals(Role.BUSINESS, business.getRole())
        );
    }

    @Test
    @DisplayName("정상적으로 Business 추가 정보가 업데이트된다.")
    void shouldUpdate_WhenBusinessDetails() {
        Business business = Business.createBusiness(account, password, name, email, registrationNumber,
                startDate, ownerName, companyName);

        business.updateDetails("서울시 강남구", "1234567890",
                Bank.KAKAO_BANK, "1234567890", "test.pdf");

        assertAll(
                () -> assertEquals("서울시 강남구", business.getBusinessAddress()),
                () -> assertEquals("1234567890", business.getOnlineSalesRegistrationNumber()),
                () -> assertEquals(Bank.KAKAO_BANK, business.getSettlementBank()),
                () -> assertEquals("1234567890", business.getSettlementAccount()),
                () -> assertEquals("test.pdf", business.getDocuments())
        );
    }

    @Nested
    @DisplayName("Business 검증 테스트")
    class ValidateBusinessTest {

        @Nested
        @DisplayName("아이디 검증 테스트")
        class ValidateAccountTest {

            @DisplayName("아이디가 null이거나 빈 문자열일 경우 예외가 발생한다")
            @ParameterizedTest
            @NullAndEmptySource
            void shouldThrowException_WhenAccountNullOrEmpty(String account) {
                assertThatThrownBy(() -> Business.createBusiness(account, password, name, email, registrationNumber,
                        startDate, ownerName, companyName))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("아이디는 필수로 입력해야 합니다.");
            }

            @DisplayName("아이디가 형식에 맞지 않을 경우 예외가 발생한다")
            @ParameterizedTest
            @ValueSource(strings = {"test", "test123"})
            void shouldThrowException_WhenAccountInvalid(String account) {
                assertThatThrownBy(() -> Business.createBusiness(account, password, name, email, registrationNumber,
                        startDate, ownerName, companyName))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("아이디의 형식이 올바르지 않습니다. (영문 8자 이상)");
            }
        }

        @Nested
        @DisplayName("비밀번호 검증 테스트")
        class ValidatePasswordTest {

            @DisplayName("비밀번호가 null이거나 빈 문자열일 경우 예외가 발생한다")
            @ParameterizedTest
            @NullAndEmptySource
            void shouldThrowException_WhenPasswordNullOrEmpty(String password) {
                assertThatThrownBy(() -> Business.createBusiness(account, password, name, email, registrationNumber,
                        startDate, ownerName, companyName))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("비밀번호는 필수로 입력해야 합니다.");
            }
        }

        @Nested
        @DisplayName("이메일 검증 테스트")
        class ValidateEmailTest {

            @DisplayName("이메일이 null이거나 빈 문자열일 경우 예외가 발생한다")
            @ParameterizedTest
            @NullAndEmptySource
            void shouldThrowException_WhenEmailNullOrEmpty(String email) {
                assertThatThrownBy(() -> Business.createBusiness(account, password, name, email, registrationNumber,
                        startDate, ownerName, companyName))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("이메일은 필수로 입력해야 합니다.");
            }

            @DisplayName("이메일이 형식에 맞지 않을 경우 예외가 발생한다")
            @ParameterizedTest
            @ValueSource(strings = {"test", "test123", "test.com"})
            void shouldThrowException_WhenEmailInvalid(String email) {
                assertThatThrownBy(() -> Business.createBusiness(account, password, name, email, registrationNumber,
                        startDate, ownerName, companyName))
                        .isInstanceOf(CustomException.class)
                        .hasMessage("이메일 형식(xxx@xxx.xx)이 올바르지 않습니다.");
            }
        }
    }
}
