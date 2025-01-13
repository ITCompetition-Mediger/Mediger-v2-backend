package net.mediger.api.member.api.dto;

import java.time.LocalDate;

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
}
