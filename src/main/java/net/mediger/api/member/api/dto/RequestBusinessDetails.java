package net.mediger.api.member.api.dto;

public record RequestBusinessDetails(
        String address,
        String ecommerceRegistrationNumber,
        String settlementAccount,
        String documents
) {
}
