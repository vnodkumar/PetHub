package com.vk.PetHub.service;

import com.vk.PetHub.dto.CartItemCreateRequest;
import com.vk.PetHub.dto.CartItemResponse;
import com.vk.PetHub.dto.CartItemUpdateRequest;
import com.vk.PetHub.exception.CartItemNotFoundException;
import com.vk.PetHub.model.CartItem;
import com.vk.PetHub.model.Product;
import com.vk.PetHub.model.User;
import com.vk.PetHub.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartRepo;
    private final UserService userService;
    private final ProductService productService;

    public CartItemService(CartItemRepository cartRepo, UserService userService,ProductService productService){
        this.cartRepo=cartRepo;
        this.productService=productService;
        this.userService=userService;
    }

    //CREATE new cartItem or UPDATE existing cartItem
    public void createCartItem( CartItemCreateRequest request) {

        Long userId = request.userId();
        Long productId = request.productId();
        Integer quantity = request.quantity();

        User user = userService.getUserEntityById(userId);
        Product product = productService.getProductEntityById(productId);

        Optional<CartItem> existing = cartRepo.findByUserAndProduct(user,product);
        CartItem cartItem;

        //Cart Exists with User id and Product id
        if(existing.isPresent()){
            cartItem = existing.get();
        }
        else{
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }
        //creating or updating quantity
        cartItem.setQuantity(quantity);

        cartRepo.save(cartItem);
    }

    public List<CartItemResponse> getCart(Long userId) {

        User user = userService.getUserEntityById(userId);
        List<CartItem> cartItems = cartRepo.findAllByUser(user);

        List<CartItemResponse> response = new ArrayList<>();

        for(CartItem cartItem:cartItems){
            response.add(
                    new CartItemResponse(
                            cartItem.getId(),
                            cartItem.getUser().getId(),
                            cartItem.getProduct().getId(),
                            cartItem.getQuantity()
                    )
            );
        }
        return response;
    }

    public void updateCartItem(Long cartItemId, CartItemUpdateRequest request) {
        //Get CartItem by id, if not found throw exception
        CartItem cartItem = cartRepo.findById(cartItemId).orElseThrow(()->new CartItemNotFoundException(cartItemId));

        cartItem.setQuantity(request.quantity());

        cartRepo.save(cartItem);
    }

    public void deleteCartItem(Long cartItemId) {
        cartRepo.deleteById(cartItemId);
    }
}
