package com.vk.PetHub.repository;

import com.vk.PetHub.model.CartItem;
import com.vk.PetHub.model.Product;
import com.vk.PetHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    boolean existsByUserAndProduct(User user, Product product);

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    List<CartItem> findAllByUser(User user);
}
