package com.vk.PetHub.service;

import com.vk.PetHub.dto.OrderItemResponse;
import com.vk.PetHub.model.*;
import com.vk.PetHub.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepo;

    private final CartService cartService;
    private final ProductService productService;

    public OrderItemService(OrderItemRepository orderItemRepo, CartService cartService, ProductService productService){
        this.orderItemRepo=orderItemRepo;

        this.cartService = cartService;
        this.productService = productService;
    }


    public void createOrderItems(Order order, List<CartItem> cart){
        List<OrderItem> orderItems = new ArrayList<>();

        User user = order.getUser();

        for(CartItem cartItem:cart){
            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            //update product stock
            product.setStockQuantity(product.getStockQuantity()- cartItem.getQuantity());
            productService.save(product);

            //delete cart item
            cartService.deleteCartItem(cartItem.getId());

            orderItems.add(orderItem);
        }

        orderItemRepo.saveAll(orderItems);
    }
    public List<OrderItemResponse> getAllOrderItems(Order order){
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();

        for(OrderItem orderItem: order.getOrderItems()){
            Product product = orderItem.getProduct();
            orderItemResponses.add(
                    new OrderItemResponse(
                            orderItem.getId(),
                            product.getId(),
                            product.getName(),
                            product.getImagePath(),
                            orderItem.getQuantity(),
                            orderItem.getPrice()
                    )
            );
        }

        return orderItemResponses;
    }
}
