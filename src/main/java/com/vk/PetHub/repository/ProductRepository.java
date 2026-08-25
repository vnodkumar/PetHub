package com.vk.PetHub.repository;

import com.vk.PetHub.model.Product;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByNameIgnoreCaseAndCategoryIgnoreCase(String name,String category);

    List<Product> findAllByCategory(String category);

    boolean existsByNameIgnoreCaseAndCategoryIgnoreCaseAndIdNot(@NotBlank(message = "Name is required") String name, String category, Long id);
}
