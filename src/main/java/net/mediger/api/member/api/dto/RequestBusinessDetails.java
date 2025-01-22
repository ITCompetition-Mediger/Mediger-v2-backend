package net.mediger.api.member.api.dto;

import java.time.LocalDate;
import net.mediger.api.member.domain.Business;

public record RequestBusinessDetails(
        String name,
        String registrationNumber,
        String address,
        String ownerName,
        LocalDate openingDate,
        String ecommerceRegistrationNumber,
        String settlementAccount,
        String documents
) {

    public Business toDetails() {
        return new Business(name, registrationNumber, address, ownerName, openingDate, ecommerceRegistrationNumber,
                settlementAccount, documents);
    }
}
