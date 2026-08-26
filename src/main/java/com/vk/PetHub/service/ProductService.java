package com.vk.PetHub.service;

import com.vk.PetHub.dto.ProductCreateRequest;
import com.vk.PetHub.dto.ProductDetailedResponse;
import com.vk.PetHub.dto.ProductSummaryResponse;
import com.vk.PetHub.dto.ProductUpdateRequest;
import com.vk.PetHub.exception.ProductAlreadyExistsException;
import com.vk.PetHub.exception.ProductNotFoundException;
import com.vk.PetHub.model.Product;
import com.vk.PetHub.repository.ProductRepository;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo){
        this.productRepo = productRepo;
    }

    public List<ProductSummaryResponse> getAllProducts(String category) {
        List<ProductSummaryResponse> response = new ArrayList<>();

        List<Product> allProducts;
        if(category!=null&&!category.isBlank()){
            allProducts = productRepo.findAllByCategory(category);
        }
        else{
            allProducts = productRepo.findAll();
        }

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
        if(productRepo.existsByNameIgnoreCaseAndCategoryIgnoreCase(request.name(), request.category())){
            throw new ProductAlreadyExistsException("Product Already Exists");
        }

        Product product = new Product();
        product.setName(request.name().trim());
        product.setCategory(request.category().trim());
        product.setDescription(request.description().trim());
        product.setImagePath(request.imagePath().trim());
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

    public Product getProductEntityById(Long id) {
        return productRepo.findById(id).orElseThrow(()->new ProductNotFoundException(id));
    }


    public void updateProduct(Long id,ProductUpdateRequest request) {

        Product product = getProductEntityById(id);
        //Product Already exists with same name in same category
        if(productRepo.existsByNameIgnoreCaseAndCategoryIgnoreCaseAndIdNot(request.name(), product.getCategory(),id)){
            throw new ProductAlreadyExistsException("Product Already Exists");
        }

        product.setName(request.name().trim());
        product.setDescription(request.description().trim());
        product.setImagePath(request.imagePath().trim());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductEntityById(id);

        product.setActive(false);

        productRepo.save(product);
    }
}
