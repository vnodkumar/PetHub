package com.vk.PetHub.dto;

import com.vk.PetHub.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailedResponse(
        Long id,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String deliveryAddress,
        String contactPhone,
        Order.OrderStatus orderStatus,
        Order.PaymentStatus paymentStatus,
        List<OrderItemResponse> items

) {
}
