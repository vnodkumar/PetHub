package com.vk.PetHub.repository;

import com.vk.PetHub.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByName(String name);
    boolean existsByCategory(String category);
}
