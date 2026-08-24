package com.vk.PetHub.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemCreateRequest(
        @NotNull(message = "User Id is required")
        @Positive(message = "User Id must be a positive number")
        Long userId,

        @NotNull(message = "Product Id is required")
        @Positive(message = "Product Id must be a positive number")
        Long productId,

        @NotNull(message = "Quantity is required")
        @Min(value = 1,message = "Quantity must atleast 1")
        @Max(value = 5,message = "Maximum Quantity per item is 5")
        Integer quantity
) {
}
