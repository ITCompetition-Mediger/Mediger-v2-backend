package net.mediger.user.domain.member;

import static net.mediger.auth.api.dto.JoinRegex.PHONE_REGEX;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import net.mediger.user.domain.Role;
import net.mediger.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends User {

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;

    @ElementCollection(targetClass = HealthConditions.class)
    @CollectionTable(name = "member_health_conditions", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "health_condition")
    private List<HealthConditions> healthConditions;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String account, String password, String name, String email, String phone) {
        super(account, password, name, email, Role.MEMBER);
        this.phone = phone;
        validatePhone();
    }

    public static Member createMember(String account, String password, String name, String email, String phone) {
        return Member.builder()
                .account(account)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .build();
    }

    private void validatePhone() {
        if (phone == null || phone.isBlank()) {
            throw new CustomException(ErrorCode.NULL_PHONE);
        }
        if (!phone.matches(PHONE_REGEX)) {
            throw new CustomException(ErrorCode.INVALID_PHONE);
        }
    }

    public void updateDetails(Gender gender, AgeRange ageRange, List<HealthConditions> healthConditions) {
        this.gender = gender;
        this.ageRange = ageRange;
        this.healthConditions = healthConditions;
    }
}
