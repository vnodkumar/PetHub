package com.vk.PetHub.exception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {
        super("Cart Empty!");
    }
}
