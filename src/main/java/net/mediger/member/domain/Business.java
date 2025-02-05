package net.mediger.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mediger.global.domain.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Table(name = "businesses")
public class Business extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String companyName;

    @Column
    private String businessAddress;

    @Column
    private String ecommerceRegistrationNumber;

    @Column
    private String settlementAccount;

    @Column
    private String documents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder(access = lombok.AccessLevel.PRIVATE)
    private Business(String account, String password, String name, String email, String registrationNumber,
                     LocalDate startDate, String ownerName, String companyName,
                     String businessAddress,
                     String ecommerceRegistrationNumber, String settlementAccount, String documents, Role role) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.email = email;
        this.registrationNumber = registrationNumber;
        this.startDate = startDate;
        this.ownerName = ownerName;
        this.companyName = companyName;
        this.businessAddress = businessAddress;
        this.ecommerceRegistrationNumber = ecommerceRegistrationNumber;
        this.settlementAccount = settlementAccount;
        this.documents = documents;
        this.role = role;
    }

    public static Business createBusiness(String account, String password, String name, String email,
            String registrationNumber, LocalDate startDate, String ownerName,
                                          String companyName) {
        return Business.builder()
                .account(account)
                .password(password)
                .name(name)
                .email(email)
                .registrationNumber(registrationNumber)
                .startDate(startDate)
                .ownerName(ownerName)
                .companyName(companyName)
                .role(Role.BUSINESS)
                .build();
    }

    public void updateBusinessDetails(String businessAddress, String ecommerceRegistrationNumber,
                                      String settlementAccount, String documents) {
        this.businessAddress = businessAddress;
        this.ecommerceRegistrationNumber = ecommerceRegistrationNumber;
        this.settlementAccount = settlementAccount;
        this.documents = documents;
    }
}
