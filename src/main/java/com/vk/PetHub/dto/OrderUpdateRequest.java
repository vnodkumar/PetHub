package com.vk.PetHub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OrderUpdateRequest(
        @NotBlank(message = "Delivery Address is required and must not be blank")
        String deliveryAddress,

        @NotBlank(message = "Contact Phone is required")
        @Pattern(
                regexp = "^$|^[6-9]\\d{9}$",
                message = "Phone number must be a valid 10-digit number"
        )
        String contactPhone
) {
}
