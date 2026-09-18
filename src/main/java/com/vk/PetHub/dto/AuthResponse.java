package com.vk.PetHub.dto;

public record AuthResponse(
        String email,
        String token
) {
}
