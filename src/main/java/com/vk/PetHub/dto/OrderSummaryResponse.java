package com.vk.PetHub.dto;

import com.vk.PetHub.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSummaryResponse(
        Long id,
        LocalDateTime date,
        Order.OrderStatus status,
        BigDecimal total
) {
}
