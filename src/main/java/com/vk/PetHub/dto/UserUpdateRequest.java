package com.vk.PetHub.dto;

public record UserUpdateRequest(
        String name,
        String address,
        String phone
) {}
