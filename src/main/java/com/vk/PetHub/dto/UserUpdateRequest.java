package com.vk.PetHub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Address is required")
        String address,

        @Pattern(
                regexp = "^$|^[6-9]\\d{9}$",
                message = "Phone number must be a valid 10-digit number"
        )
        @NotBlank(message = "Phone number is required")
        String phone
) {}
