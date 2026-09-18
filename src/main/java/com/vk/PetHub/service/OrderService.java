package com.vk.PetHub.service;

import com.vk.PetHub.dto.*;
import com.vk.PetHub.exception.*;
import com.vk.PetHub.model.*;
import com.vk.PetHub.repository.OrderRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;

    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public OrderService(OrderRepository orderRepo, CartService cartService, UserService userService, ProductService productService, OrderItemService orderItemService){
        this.cartService = cartService;
        this.userService = userService;
        this.orderItemService = orderItemService;
        this.productService = productService;

        this.orderRepo = orderRepo;
    }

    @Transactional
    public void createOrder(Long userId,OrderCreateRequest request) {
        //get user
        User user = userService.getUserEntityById(userId);

        //get cart
        List<CartItem> cart = cartService.getCartEntity(user);

        //if cart is empty throw an exception
        if(cart.isEmpty()){
            //throw empty cart exception
            throw new CartEmptyException();
        }

        BigDecimal totalAmount = checkOrderIssues(cart);

        //create an order
        Order order = new Order();
        order.setDeliveryAddress(request.deliveryAddress().trim());
        order.setContactPhone(request.contactPhone().trim());
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        orderRepo.save(order);

        //Create Order Items
        orderItemService.createOrderItems(order,cart);
    }

    public static BigDecimal checkOrderIssues(List<CartItem> cart){
        //error message  and list of issues
        List<CartIssue> cartIssues = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        //Verify cart that it doesn't contain non active item present and quantity doesn't exceeds stockQuantity
        for(CartItem cartItem:cart){
            //get product
            Product product = cartItem.getProduct();

            //Not Active
            if(!product.isActive()){
                cartIssues.add(new CartIssue(product.getId(),product.getName(),IssueReason.NO_LONGER_AVAILABLE));
                continue;
            }
            //Quantity exceeds
            if(cartItem.getQuantity()>product.getStockQuantity()){
                cartIssues.add(new CartIssue(product.getId(),product.getName(),IssueReason.OUT_OF_STOCK));
                continue;
            }

            totalAmount = totalAmount.add(
                    product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );

        }
        //throw exception if cart is not valid
        if(!cartIssues.isEmpty())
            throw new CartCheckoutException(cartIssues);

        return totalAmount;
    }

    public void cancelOrder(Long orderId,Long userId){
        Order order = orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException(orderId));

        //Ownership Check
        checkOwnership(order,userId);

        //Delivered?
        if(order.getOrderStatus() == Order.OrderStatus.DELIVERED){
            //throw CannotCancelOrderException
            throw new OrderCannotBeCancelledException();
        }
        if(order.getOrderStatus()== Order.OrderStatus.CANCELLED)    return;

        //Update Stock Quantity of each OrderItem
        //Get All OrderItems of That order
        List<OrderItemResponse> orderItems = orderItemService.getAllOrderItems(order);

        for(OrderItemResponse orderItem:orderItems){
            //Get Product Entity
            Product product = productService.getProductEntityById(orderItem.productId());
            //Update Stock
            product.setStockQuantity(product.getStockQuantity()+orderItem.quantity());

            productService.save(product);
        }

        //set order status as canceled and
        order.setOrderStatus(Order.OrderStatus.CANCELLED);

        orderRepo.save(order);
    }

    public void updateOrder(Long orderId, Long userId, OrderUpdateRequest request){
        Order order = orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException(orderId));

        //Ownership Check
        checkOwnership(order,userId);

        //if order cancelled or delivered we can't update order
        if(order.getOrderStatus()== Order.OrderStatus.DELIVERED||order.getOrderStatus()== Order.OrderStatus.CANCELLED){
            throw new OrderCannotBeUpdatedException();
        }
        order.setDeliveryAddress(request.deliveryAddress().trim());
        order.setContactPhone(request.contactPhone().trim());

        orderRepo.save(order);
    }

    public List<OrderSummaryResponse> orderHistory( Long userId) {
        User user = userService.getUserEntityById(userId);
        List<Order> orders = orderRepo.findAllByUser(user);

        List<OrderSummaryResponse> response = new ArrayList<>();

        for(Order order:orders){
            response.add(
                    new OrderSummaryResponse(
                            order.getId(),
                            order.getOrderDate(),
                            order.getOrderStatus(),
                            order.getTotalAmount()
                    )
            );
        }

        return response;
    }

    public OrderDetailedResponse getOrder(Long orderId, Long userId){
        Order order = orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException(orderId));

        //Ownership Check
        checkOwnership(order,userId);

        List<OrderItemResponse> orderItemResponses = orderItemService.getAllOrderItems(order);

        return new OrderDetailedResponse(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getDeliveryAddress(),
                order.getContactPhone(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                orderItemResponses
        );
    }
    public static void checkOwnership(Order order,Long userId){
        if(!order.getUser().getId().equals(userId)) throw new AccessDeniedException("Access Denied");
    }
}
