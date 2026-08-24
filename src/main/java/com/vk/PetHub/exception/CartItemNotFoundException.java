package com.vk.PetHub.exception;

public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException(Long id){
        super("CartItem Not Found With Id:"+id);
    }
}
