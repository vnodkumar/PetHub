package com.vk.PetHub.exception;

public class OrderCannotBeCancelledException extends RuntimeException {
    public OrderCannotBeCancelledException() {
        super("Order Cannot Be Cancelled");
    }
}
