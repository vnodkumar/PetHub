package com.vk.PetHub.service;

import com.vk.PetHub.dto.ProductCreateRequest;
import com.vk.PetHub.dto.ProductDetailedResponse;
import com.vk.PetHub.dto.ProductSummaryResponse;
import com.vk.PetHub.exception.ProductAlreadyExistsException;
import com.vk.PetHub.exception.ProductNotFoundException;
import com.vk.PetHub.exception.UserAlreadyExistsException;
import com.vk.PetHub.model.Product;
import com.vk.PetHub.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    ProductService(ProductRepository productRepo){
        this.productRepo = productRepo;
    }

    public List<ProductSummaryResponse> getAllProducts() {
        List<ProductSummaryResponse> response = new ArrayList<>();
        List<Product> allProducts = productRepo.findAll();

        for(Product product:allProducts){
            response.add(
                    new ProductSummaryResponse(
                            product.getId(),
                            product.getName(),
                            product.getCategory(),
                            product.getImagePath(),
                            product.getPrice()
                    )
            );
        }

        return response;
    }

    public void createProduct(ProductCreateRequest request) {

        //Product Already exists with same name in same category
        if(productRepo.existsByName(request.name())&&productRepo.existsByCategory(request.category())){
            throw new ProductAlreadyExistsException("Product Already Exists");
        }

        Product product = new Product();
        product.setName(request.name());
        product.setCategory(request.category());
        product.setDescription(request.description());
        product.setImagePath(request.imagePath());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        productRepo.save(product);
    }

    public ProductDetailedResponse getProductById(Long id) {

        Product product = productRepo.findById(id).orElseThrow(()->new ProductNotFoundException(id));

        return new ProductDetailedResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getImagePath(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
