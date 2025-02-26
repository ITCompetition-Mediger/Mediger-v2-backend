package net.mediger.user.domain.business;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mediger.user.domain.Role;
import net.mediger.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Business extends User {

    private String registrationNumber;

    private String startDate;

    private String ownerName;

    private String companyName;

    private String businessAddress;

    private String onlineSalesRegistrationNumber;

    @Enumerated(EnumType.STRING)
    private Bank settlementBank;

    private String settlementAccount;

    private String document;

    @Builder(access = lombok.AccessLevel.PRIVATE)
    private Business(String account, String password, String name, String email, String registrationNumber,
                     String startDate, String ownerName, String companyName, String businessAddress,
                     String onlineSalesRegistrationNumber, Bank settlementBank, String settlementAccount,
                     String document) {
        super(account, password, name, email, Role.BUSINESS);
        this.registrationNumber = registrationNumber;
        this.startDate = startDate;
        this.ownerName = ownerName;
        this.companyName = companyName;
        this.businessAddress = businessAddress;
        this.onlineSalesRegistrationNumber = onlineSalesRegistrationNumber;
        this.settlementBank = settlementBank;
        this.settlementAccount = settlementAccount;
        this.document = document;
    }

    public static Business createBusiness(String account, String password, String name, String email,
                                          String registrationNumber, String startDate, String ownerName,
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
                .build();
    }

    public void updateDetails(String businessAddress, String onlineSalesRegistrationNumber,
                              Bank settlementBank, String settlementAccount, String document) {
        this.businessAddress = businessAddress;
        this.onlineSalesRegistrationNumber = onlineSalesRegistrationNumber;
        this.settlementBank = settlementBank;
        this.settlementAccount = settlementAccount;
        this.document = document;
    }
}
