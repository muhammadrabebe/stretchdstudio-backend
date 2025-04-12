package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.ProductDTO;
import com.strechdstudio.app.model.Product;
import com.strechdstudio.app.service.BookingService;
import com.strechdstudio.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService = new ProductService();

    // Constructor-based injection (Recommended)
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        ApiResponse<List<ProductDTO>> response = new ApiResponse<>("success", 200, products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProduct(@PathVariable UUID id) {
        ProductDTO product = productService.getProductById(id);
        ApiResponse<ProductDTO> response = new ApiResponse<>("success", 200, product);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductDTO product) {
        Product saved = productService.createProduct(product);
        ApiResponse<Product> response =
                new ApiResponse<>("Product " + product.getName() + " has been created successfully", 200, saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO product) {
        Product updated = productService.updateProduct(id, product);
        ApiResponse<Product> response =
                new ApiResponse<>("Product " + product.getName() + " has been updated successfully", 200, updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        ApiResponse<Void> response = new ApiResponse<>("Product has been deleted successfully", 200, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}

