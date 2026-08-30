package com.vk.PetHub.dto;

public record CartIssue(
        Long productId,
        String productName,
        IssueReason reason
) {
}
