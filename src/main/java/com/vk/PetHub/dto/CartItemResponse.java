package com.vk.PetHub.dto;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Long userId,
        String productName,
        BigDecimal price,
        String imagePath,
        Integer quantity
) {
}
