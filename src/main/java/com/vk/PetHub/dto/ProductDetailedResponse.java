package com.vk.PetHub.dto;

import java.math.BigDecimal;

public record ProductDetailedResponse(
        Long id,
        String name,
        String category,
        String description,
        String imagePath,
        BigDecimal price,
        Integer stockQuantity
) {
}
