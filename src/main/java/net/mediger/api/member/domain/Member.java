package net.mediger.api.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private int age;

    @Column(name = "health_concerns")
    private String healthConcerns;

    @Column(name = "health_focus")
    private String healthFocus;

    @Column(name = "health_chronic_disease")
    private String healthChronicDisease;

    @Column(name = "health_interested_disease")
    private String healthInterestedDisease;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "business_address")
    private String businessAddress;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "ecommerce_registration_number")
    private String ecommerceRegistrationNumber;

    @Column(name = "settlement_account")
    private String settlementAccount;

    @Column(name = "documents")
    private String documents;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String account, String password, String name, String email, String phone, Role role) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public static Member createMember(String account, String password, String name, String email, String phone) {
        return Member.builder()
                .account(account)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .role(Role.MEMBER)
                .build();
    }

    public static Member createBusinessMember(String account, String password, String name, String email,
                                              String phone) {
        return Member.builder()
                .account(account)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .role(Role.BUSINESS)
                .build();
    }

    public void updateDetails(Gender gender, int age, String healthConcerns, String healthFocus, String healthChronicDisease,
                              String healthInterestedDisease) {
        authorizedMember(role);
        this.gender = gender;
        this.age = age;
        this.healthConcerns = healthConcerns;
        this.healthFocus = healthFocus;
        this.healthChronicDisease = healthChronicDisease;
        this.healthInterestedDisease = healthInterestedDisease;
    }

    public void updateBusinessDetails(String businessName, String registrationNumber, String businessAddress,
                                      String ownerName, LocalDate openingDate, String ecommerceRegistrationNumber,
                                      String settlementAccount, String documents) {
        authorizedBusiness(role);
        this.businessName = businessName;
        this.registrationNumber = registrationNumber;
        this.businessAddress = businessAddress;
        this.ownerName = ownerName;
        this.openingDate = openingDate;
        this.ecommerceRegistrationNumber = ecommerceRegistrationNumber;
        this.settlementAccount = settlementAccount;
        this.documents = documents;
    }

    public void authorizedMember(Role role) {
        if (!role.equals(Role.MEMBER)) {
            throw new IllegalArgumentException("잘못된 권한입니다.");
        }
    }

    public void authorizedBusiness(Role role) {
        if (!role.equals(Role.BUSINESS)) {
            throw new IllegalArgumentException("잘못된 권한입니다.");
        }
    }
}
