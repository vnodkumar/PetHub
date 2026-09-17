package com.vk.PetHub.controller;

import com.vk.PetHub.dto.CartItemCreateRequest;
import com.vk.PetHub.dto.CartItemResponse;
import com.vk.PetHub.dto.CartItemUpdateRequest;
import com.vk.PetHub.model.CustomUserDetails;
import com.vk.PetHub.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@Validated
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService= cartService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCartItem(@AuthenticationPrincipal CustomUserDetails userDetails,@Valid @RequestBody CartItemCreateRequest request){
        Long userId = userDetails.getId();
        cartService.createCartItem(userId,request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getId();
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCartItem(@AuthenticationPrincipal CustomUserDetails userDetails,@PathVariable @Positive(message = "CartItem Id must be positive") Long id,@Valid @RequestBody CartItemUpdateRequest request){
        Long userId = userDetails.getId();
        cartService.updateCartItem(id,userId,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCartItem(@AuthenticationPrincipal CustomUserDetails userDetails,@PathVariable @Positive(message = "CartItem Id must be positive") Long id){
        Long userId = userDetails.getId();
        cartService.deleteCartItem(id,userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
