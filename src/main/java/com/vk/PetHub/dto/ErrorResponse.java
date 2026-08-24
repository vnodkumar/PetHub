package com.vk.PetHub.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp
) {
    public ErrorResponse(String message,int status){
        this(message,status,LocalDateTime.now());
    }
}
