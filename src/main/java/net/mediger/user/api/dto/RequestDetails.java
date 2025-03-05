package net.mediger.user.api.dto;

import java.util.List;

public record RequestDetails(
        String gender,
        String age,
        List<String> healthConditions
) {
}
