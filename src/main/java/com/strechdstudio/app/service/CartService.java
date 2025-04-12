package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.CartDTO;
import com.strechdstudio.app.model.*;
import com.strechdstudio.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CodeLkupRepository codeLkupRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream().map(cart -> {
            CartDTO dto = new CartDTO();
            dto.setCartId(cart.getCartId());
            dto.setCustomerId(cart.getCustomer().getCustomerId());
            dto.setStatusId(cart.getStatus().getCodeLkupId());
            dto.setAddDate(cart.getAddDate());
            dto.setAddWho(cart.getAddWho());
            dto.setEditDate(cart.getEditDate());
            dto.setEditWho(cart.getEditWho());
            return dto;
        }).collect(Collectors.toList());
    }

    public Cart getCartById(UUID id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));
    }

    public Cart createCart(Cart cart) {
        cart.setAddDate(LocalDateTime.now());
        cart.setEditDate(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public Cart updateCart(UUID id, Cart updatedCart) {
        Cart existing = getCartById(id);
        updatedCart.setCartId(existing.getCartId());
        updatedCart.setAddDate(existing.getAddDate());
        updatedCart.setEditDate(LocalDateTime.now());
        return cartRepository.save(updatedCart);
    }

    public void deleteCart(UUID id) {
        if (!cartRepository.existsById(id)) {
            throw new NoSuchElementException("Cart not found");
        }
        cartRepository.deleteById(id);
    }

//    public List<CartDTO> getCartsByCustomer(int customerId) {
//        return cartRepository.findByCustomer_CustomerId(customerId).stream().map(cart -> {
//            CartDTO dto = new CartDTO();
//            dto.setCartId(cart.getCartId());
//            dto.setCustomerId(cart.getCustomer().getCustomerId());
//            dto.setStatusId(cart.getStatus().getCodeLkupId());
//            dto.setCustomerName(cart.getCustomer().getFullname());
//            dto.setStatus(cart.getStatus().getCode());
//            dto.setAddDate(cart.getAddDate());
//            dto.setAddWho(cart.getAddWho());
//            dto.setEditDate(cart.getEditDate());
//            dto.setEditWho(cart.getEditWho());
//            return dto;
//        }).collect(Collectors.toList());
//    }

//    public Cart getActiveCartByCustomer(int customerId, int activeStatusId) {
//        return cartRepository.findByCustomer_CustomerId(customerId)
//                .orElseGet(() -> {
//                    Customer customer = customerRepository.findById(customerId)
//                            .orElseThrow(() -> new NoSuchElementException("Customer not found"));
//
//                    CodeLkup activeStatus = codeLkupRepository.findByCodelist_ListNameAndCode("CARTSTATUS","New")
//                            .orElseThrow(() -> new NoSuchElementException("New Status not found"));
//
//                    Cart newCart = new Cart();
//                    newCart.setCustomer(customer);
//                    newCart.setStatus(activeStatus);
//                    newCart.setAddDate(LocalDateTime.now());
//                    return cartRepository.save(newCart);
//                });
//    }

    public Cart getActiveCartByCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        CodeLkup activeStatus = codeLkupRepository.findByCodelist_ListNameAndCode("CARTSTATUS", "New")
                .orElseThrow(() -> new NoSuchElementException("New Status not found"));

        return cartRepository.findByCustomer_CustomerIdAndStatus_CodeLkupId(customerId, activeStatus.getCodeLkupId());
    }

    public Cart addToCart(int customerId, UUID productId, int quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CodeLkup activeStatus = codeLkupRepository.findByCodelist_ListNameAndCode("CARTSTATUS", "New")
                .orElseThrow(() -> new RuntimeException("New Status not found"));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }

        Cart cart = cartRepository.findByCustomer_CustomerIdAndStatus_CodeLkupId(customerId, activeStatus.getCodeLkupId());
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            cart.setStatus(activeStatus);
            cart.setAddDate(LocalDateTime.now());
            cart.setEditDate(LocalDateTime.now());
            cart.setAddWho(customer.getFullname());
            cart.setEditWho(customer.getFullname());

            cart = cartRepository.save(cart);
        }

        // Check if item already exists in the cart
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId);
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setEditDate(LocalDateTime.now());
            existingItem.setEditWho(customer.getFullname());
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            newItem.setAddDate(LocalDateTime.now());
            newItem.setEditDate(LocalDateTime.now());
            newItem.setAddWho(customer.getFullname());
            newItem.setEditWho(customer.getFullname());
            cartItemRepository.save(newItem);
        }

        return null;
    }

}
