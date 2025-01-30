package net.mediger.api.auth.api.dto;

import java.time.LocalDate;
import net.mediger.api.member.domain.Business;

public record RequestBusinessDetails(
        String address,
        LocalDate openingDate,
        String ecommerceRegistrationNumber,
        String settlementAccount,
        String documents
) {

    public Business toDetails() {
        return new Business(address, openingDate, ecommerceRegistrationNumber, settlementAccount, documents);
    }
}
