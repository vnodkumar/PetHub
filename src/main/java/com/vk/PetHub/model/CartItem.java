package com.vk.PetHub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cart_items",
    uniqueConstraints = {@UniqueConstraint(
            name = "uk_cart_user_product",
            columnNames = {"user_id","product_id"}
    )})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    @ToString.Exclude
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
}

