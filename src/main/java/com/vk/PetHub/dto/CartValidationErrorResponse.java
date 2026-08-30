package com.vk.PetHub.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CartValidationErrorResponse(
        String message,
        List<CartIssue> issues,
        int status,
        LocalDateTime timestamp
) {
    public CartValidationErrorResponse(String message,List<CartIssue> issues,int status){
        this(message,issues,status,LocalDateTime.now());
    }
}
