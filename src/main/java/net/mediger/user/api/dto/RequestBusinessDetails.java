package net.mediger.user.api.dto;

public record RequestBusinessDetails(
        String address,
        String onlineSalesRegistrationNumber,
        String settlementBank,
        String settlementAccount,
        String documents
) {
}
