package com.vk.PetHub.repository;

import com.vk.PetHub.model.Order;
import com.vk.PetHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUser(User user);
}
