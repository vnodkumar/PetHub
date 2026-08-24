package com.vk.PetHub.controller;

import com.vk.PetHub.dto.ProductCreateRequest;
import com.vk.PetHub.dto.ProductDetailedResponse;
import com.vk.PetHub.dto.ProductSummaryResponse;
import com.vk.PetHub.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService){
        this.productService=productService;
    }
    @GetMapping("/api/products")
    public ResponseEntity<List<ProductSummaryResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductDetailedResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/api/products")
    public ResponseEntity<HttpStatus> createProduct(@Valid @RequestBody ProductCreateRequest request){
        productService.createProduct(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
