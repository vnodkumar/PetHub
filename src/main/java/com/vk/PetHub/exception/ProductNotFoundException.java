package com.vk.PetHub.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("Product Not Found with id:"+id);
    }

}
