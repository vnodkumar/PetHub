package com.vk.PetHub.dto;

import java.math.BigDecimal;

public record ProductSummaryResponse(
        Long id,
        String name,
        String category,
        String imagePath,
        BigDecimal price
) {
}
