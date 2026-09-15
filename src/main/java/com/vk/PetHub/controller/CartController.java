package com.vk.PetHub.controller;

import com.vk.PetHub.dto.CartItemCreateRequest;
import com.vk.PetHub.dto.CartItemResponse;
import com.vk.PetHub.dto.CartItemUpdateRequest;
import com.vk.PetHub.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Validated
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService= cartService;
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createCartItem(@Valid @RequestBody CartItemCreateRequest request){
        cartService.createCartItem(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CartItemResponse>> getCart(@PathVariable @Positive(message = "User Id must be positive") Long id){
        return ResponseEntity.ok(cartService.getCart(id));
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<HttpStatus> updateCartItem(@PathVariable @Positive(message = "CartItem Id must be positive") Long cartItemId,@Valid @RequestBody CartItemUpdateRequest request){
        cartService.updateCartItem(cartItemId,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable @Positive(message = "CartItem Id must be positive") Long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
