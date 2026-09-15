package com.vk.PetHub.controller;

import com.vk.PetHub.dto.OrderCreateRequest;
import com.vk.PetHub.dto.OrderDetailedResponse;
import com.vk.PetHub.dto.OrderSummaryResponse;
import com.vk.PetHub.dto.OrderUpdateRequest;
import com.vk.PetHub.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createOrder(@Valid @RequestBody OrderCreateRequest request){
        orderService.createOrder(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateOrder(@PathVariable @Positive(message = "Oder Id must be positive") Long id, @Valid @RequestBody OrderUpdateRequest request){
        orderService.updateOrder(id,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderSummaryResponse>> orderHistory(@PathVariable @Positive(message = "User Id must be positive") Long id){
        return new ResponseEntity<>(orderService.orderHistory(id),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailedResponse> getOrder(@PathVariable @Positive(message = "Order Id must be positive") Long id){
        return  new ResponseEntity<>(orderService.getOrder(id),HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<HttpStatus> cancelOrder(@PathVariable @Positive(message = "Order Id must be positive") Long id){
        orderService.cancelOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
