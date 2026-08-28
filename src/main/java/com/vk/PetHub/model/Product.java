package com.vk.PetHub.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name="stock_quantity",nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItem> orderItems;
}