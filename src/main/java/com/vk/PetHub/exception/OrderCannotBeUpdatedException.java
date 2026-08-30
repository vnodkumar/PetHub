package com.vk.PetHub.exception;

public class OrderCannotBeUpdatedException extends RuntimeException {
    public OrderCannotBeUpdatedException() {
        super("Order Cannot Be Updated");
    }
}
