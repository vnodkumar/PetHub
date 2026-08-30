package com.vk.PetHub.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order Not Found with Id:"+id);
    }
}
