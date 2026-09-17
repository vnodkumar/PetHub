package com.vk.PetHub.controller;

import com.vk.PetHub.dto.ProductCreateRequest;
import com.vk.PetHub.dto.ProductDetailedResponse;
import com.vk.PetHub.dto.ProductSummaryResponse;
import com.vk.PetHub.dto.ProductUpdateRequest;
import com.vk.PetHub.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    //Get All Products
    @GetMapping
    public ResponseEntity<List<ProductSummaryResponse>> getAllProducts(@RequestParam(required = false) String category){
        return ResponseEntity.ok(productService.getAllProducts(category));
    }
    //Get One Product
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailedResponse> getProductById(@PathVariable @Positive(message = "Product Id must be positive") Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    //Create New Product
    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@Valid @RequestBody ProductCreateRequest request){
        productService.createProduct(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //Update Product
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable @Positive(message = "Product Id should be positive") Long id,@Valid @RequestBody ProductUpdateRequest request){
        productService.updateProduct(id,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable @Positive(message = "Product Id should be positive") Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
