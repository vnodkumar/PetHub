package com.vk.PetHub.controller;

import com.vk.PetHub.dto.CartItemCreateRequest;
import com.vk.PetHub.dto.CartItemResponse;
import com.vk.PetHub.dto.CartItemUpdateRequest;
import com.vk.PetHub.model.CartItem;
import com.vk.PetHub.service.CartItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class CartItemController {

    private final CartItemService cartService;

    CartItemController(CartItemService cartItemService){
        this.cartService=cartItemService;
    }

    @PostMapping("/api/cart")
    public ResponseEntity<HttpStatus> createCartItem(@Valid @RequestBody CartItemCreateRequest request){
        cartService.createCartItem(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/cart/{userId}")
    public ResponseEntity<List<CartItemResponse>> getCart(@PathVariable @Positive(message = "User Id must be positive") Long userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PutMapping("/api/cart/{cartItemId}")
    public ResponseEntity<HttpStatus> updateCartItem(@PathVariable @Positive(message = "CartItem Id must be positive") Long cartItemId,@Valid @RequestBody CartItemUpdateRequest request){
        cartService.updateCartItem(cartItemId,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/cart/{cartItemId}")
    public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable @Positive(message = "CartItem Id must be positive") Long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
