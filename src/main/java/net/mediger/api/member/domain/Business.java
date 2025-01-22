package net.mediger.api.member.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "businessName", column = @Column(name = "business_name")),
        @AttributeOverride(name = "registrationNumber", column = @Column(name = "registration_number")),
        @AttributeOverride(name = "businessAddress", column = @Column(name = "business_address")),
        @AttributeOverride(name = "ownerName", column = @Column(name = "owner_name")),
        @AttributeOverride(name = "openingDate", column = @Column(name = "opening_date")),
        @AttributeOverride(name = "ecommerceRegistrationNumber", column = @Column(name = "ecommerce_registration_number")),
        @AttributeOverride(name = "settlementAccount", column = @Column(name = "settlement_account")),
        @AttributeOverride(name = "documents", column = @Column(name = "documents"))
})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class Business {

    private String businessName;

    private String registrationNumber;

    private String businessAddress;

    private String ownerName;

    private LocalDate openingDate;

    private String ecommerceRegistrationNumber;

    private String settlementAccount;

    private String documents;
}
