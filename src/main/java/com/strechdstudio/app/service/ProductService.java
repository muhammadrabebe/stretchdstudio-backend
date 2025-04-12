package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.*;
import com.strechdstudio.app.model.*;
import com.strechdstudio.app.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CodeLkupRepository codeLkupRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setImageUrl(product.getImageUrl());
            dto.setStockQuantity(product.getStockQuantity());
            dto.setCategoryId(product.getCategory().getCodeLkupId());
            dto.setCategory(product.getCategory().getCode());
            dto.setAddDate(product.getAddDate());
            dto.setAddWho(product.getAddWho());
            dto.setEditDate(product.getEditDate());
            dto.setEditWho(product.getEditWho());
            return dto;
        }).collect(Collectors.toList());
    }

    public ProductDTO getProductById(UUID id) {
        return productRepository.findById(id)
                .map(ProductDTO::new)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public Product createProduct(ProductDTO productDTO) {

        CodeLkup category = codeLkupRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Product existingProduct = new Product();

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());
        existingProduct.setCategory(category);
        existingProduct.setAddWho(productDTO.getAddWho());
        existingProduct.setEditWho(productDTO.getEditWho());
        existingProduct.setAddDate(LocalDateTime.now());
        existingProduct.setEditDate(LocalDateTime.now());

        return productRepository.save(existingProduct);
    }

    public Product updateProduct(UUID productId, ProductDTO productDTO) {

        CodeLkup category = codeLkupRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Product updatedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        updatedProduct.setName(productDTO.getName());
        updatedProduct.setDescription(productDTO.getDescription());
        updatedProduct.setImageUrl(productDTO.getImageUrl());
        updatedProduct.setPrice(productDTO.getPrice());
        updatedProduct.setStockQuantity(productDTO.getStockQuantity());
        updatedProduct.setCategory(category);
        updatedProduct.setEditWho(productDTO.getEditWho());
        updatedProduct.setEditDate(LocalDateTime.now());

        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public List<ProductDTO> getProductsByCategory(int categoryId) {
        return productRepository.findByCategory_CodeLkupId(categoryId).stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setImageUrl(product.getImageUrl());
            dto.setStockQuantity(product.getStockQuantity());
            dto.setCategoryId(product.getCategory().getCodeLkupId());
            dto.setAddDate(product.getAddDate());
            dto.setAddWho(product.getAddWho());
            dto.setEditDate(product.getEditDate());
            dto.setEditWho(product.getEditWho());
            return dto;
        }).collect(Collectors.toList());
    }

}
