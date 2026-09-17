package com.vk.PetHub.controller;

import com.vk.PetHub.dto.OrderCreateRequest;
import com.vk.PetHub.dto.OrderDetailedResponse;
import com.vk.PetHub.dto.OrderSummaryResponse;
import com.vk.PetHub.dto.OrderUpdateRequest;
import com.vk.PetHub.model.CustomUserDetails;
import com.vk.PetHub.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails,@Valid @RequestBody OrderCreateRequest request){
        Long userId = customUserDetails.getId();
        orderService.createOrder(userId,request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateOrder(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable @Positive(message = "Oder Id must be positive") Long id, @Valid @RequestBody OrderUpdateRequest request){
        Long userId = userDetails.getId();
        orderService.updateOrder(id,userId,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryResponse>> orderHistory(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getId();
        return new ResponseEntity<>(orderService.orderHistory(userId),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailedResponse> getOrder(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable @Positive(message = "Order Id must be positive") Long id){
        Long userId = userDetails.getId();
        return  new ResponseEntity<>(orderService.getOrder(id,userId),HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<HttpStatus> cancelOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable @Positive(message = "Order Id must be positive") Long id){
        Long userId = customUserDetails.getId();
        orderService.cancelOrder(id,userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
