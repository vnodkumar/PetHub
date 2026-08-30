package com.vk.PetHub.exception;

import com.vk.PetHub.dto.CartIssue;
import lombok.Getter;

import java.util.List;

@Getter
public class CartCheckoutException extends RuntimeException {
    private final List<CartIssue> issues;
    public CartCheckoutException(List<CartIssue> issues)
    {
        super("Some items in your cart need attention");
        this.issues=issues;
    }

}
