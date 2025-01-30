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
        @AttributeOverride(name = "businessAddress", column = @Column(name = "business_address")),
        @AttributeOverride(name = "openingDate", column = @Column(name = "opening_date")),
        @AttributeOverride(name = "ecommerceRegistrationNumber", column = @Column(name = "ecommerce_registration_number")),
        @AttributeOverride(name = "settlementAccount", column = @Column(name = "settlement_account")),
        @AttributeOverride(name = "documents", column = @Column(name = "documents"))
})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class Business {
    private String businessAddress;

    private LocalDate openingDate;

    private String ecommerceRegistrationNumber;

    private String settlementAccount;

    private String documents;
}
