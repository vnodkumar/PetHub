package com.vk.PetHub.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,        // Included so the client knows the generated ID
        String name,
        String email,
        String role,
        String phone,
        String address,
        LocalDateTime createdAt
){}
