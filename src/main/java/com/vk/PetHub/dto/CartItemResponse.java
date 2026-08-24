package com.vk.PetHub.dto;

public record CartItemResponse(
        Long id,
        Long userId,
        Long productId,
        Integer quantity
) {
}
