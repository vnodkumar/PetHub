package com.vk.PetHub.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name="order_date",nullable = false)
    private LocalDateTime orderDate;

    @Column(name="total_amount",nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "delivery_address",nullable = false)
    private String deliveryAddress;

    @Column(name = "contact_phone",nullable = false)
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status",nullable = false)
    private OrderStatus orderStatus = OrderStatus.CONFIRMED;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status",nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItem> orderItems;

    @PrePersist
    protected void onCreated(){
        if(orderDate==null) orderDate=LocalDateTime.now();
    }


    public enum OrderStatus{
        CONFIRMED,DELIVERED,CANCELLED
    }
    public enum PaymentStatus{
        PENDING,PAID
    }
}
