package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.CartItemDTO;
import com.strechdstudio.app.model.*;
import com.strechdstudio.app.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CodeLkupRepository codeLkupRepository;


    @Autowired
    private CustomerRepository customerRepository;

    public List<CartItemDTO> getAllCartItems() {
        return cartItemRepository.findAll().stream().map(item -> {
            CartItemDTO dto = new CartItemDTO();
            dto.setCartItemId(item.getCartItemId());
            dto.setCartId(item.getCart().getCartId());
            dto.setProductId(item.getProduct().getProductId());
            dto.setQuantity(item.getQuantity());
            dto.setAddDate(item.getAddDate());
            dto.setAddWho(item.getAddWho());
            dto.setEditDate(item.getEditDate());
            dto.setEditWho(item.getEditWho());
            return dto;
        }).collect(Collectors.toList());
    }

    public CartItem getCartItemById(UUID id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cart item not found"));
    }

    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setAddDate(LocalDateTime.now());
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(UUID id, CartItem updatedItem) {
        CartItem existing = getCartItemById(id);
        updatedItem.setCartItemId(existing.getCartItemId());
        updatedItem.setAddDate(existing.getAddDate());
        updatedItem.setEditDate(LocalDateTime.now());
        return cartItemRepository.save(updatedItem);
    }

    public void deleteCartItem(UUID id) {
        if (!cartItemRepository.existsById(id)) {
            throw new NoSuchElementException("Cart item not found");
        }
        cartItemRepository.deleteById(id);
    }

    public CartItem addProductToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        // Check if product already exists in cart
        Optional<CartItem> existingItemOpt = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(cartId, productId);

        CartItem cartItem;
        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setEditDate(LocalDateTime.now());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setAddDate(LocalDateTime.now());
        }

        return cartItemRepository.save(cartItem);
    }

    public CartItem addProductToCartWithStockCheck(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cartId, productId);

        CartItem cartItem;
        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            if (product.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Total quantity exceeds stock for: " + product.getName());
            }
            cartItem.setQuantity(newQuantity);
            cartItem.setEditDate(LocalDateTime.now());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setAddDate(LocalDateTime.now());
        }

        return cartItemRepository.save(cartItem);
    }


    public List<CartItemDTO> getItemsByCustomerActiveCart(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        CodeLkup activeStatus = codeLkupRepository.findByCodelist_ListNameAndCode("CARTSTATUS", "New")
                .orElseThrow(() -> new RuntimeException("New Status not found"));

        Cart cart = cartRepository.findByCustomer_CustomerIdAndStatus_CodeLkupId(customerId, activeStatus.getCodeLkupId());
        if (cart != null) {
            List<CartItem> items = cartItemRepository.findByCart_CartId(cart.getCartId());
            return items.stream()
                    .map(item -> {
                        CartItemDTO dto = new CartItemDTO();
                        dto.setCartItemId(item.getCartItemId());
                        dto.setProductName(item.getProduct().getName());
                        dto.setPrice(item.getProduct().getPrice());
                        dto.setCartId(item.getCart().getCartId());
                        dto.setProductId(item.getProduct().getProductId());
                        dto.setQuantity(item.getQuantity());
                        dto.setImageURL(item.getProduct().getImageUrl()); // assuming Product has imageURL
                        return dto;
                    })
                    .collect(Collectors.toList());
        } else
            return null;

    }


}
