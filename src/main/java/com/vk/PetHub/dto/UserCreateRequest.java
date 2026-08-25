package com.vk.PetHub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6,message = "Password must contain atleast 6 characters")
        String password,

        @Pattern(
                regexp = "^$|^[6-9]\\d{9}$",
                message = "Phone number must be a valid 10-digit number"
        )
        String phone,

        String address
) {}
