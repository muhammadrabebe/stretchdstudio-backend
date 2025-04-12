package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.AddToCartRequest;
import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.CartDTO;
import com.strechdstudio.app.model.Cart;
import com.strechdstudio.app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {

    CartService cartService = new CartService();

    // Constructor-based injection (Recommended)
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartDTO>>> getAllCarts() {
        List<CartDTO> carts = cartService.getAllCarts();
        ApiResponse<List<CartDTO>> response = new ApiResponse<>("success", 200, carts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cart>> getCart(@PathVariable UUID id) {
        Cart cart = cartService.getCartById(id);
        ApiResponse<Cart> response = new ApiResponse<>("success", 200, cart);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
        public ResponseEntity<ApiResponse<Cart>> addToCart(@RequestBody AddToCartRequest request) {
        Cart updatedCart = cartService.addToCart(request.getCustomerId(), request.getProductId(), request.getQuantity());
        ApiResponse<Cart> response = new ApiResponse<>("Item added to cart", 200, updatedCart);
        return ResponseEntity.ok(response);
    }



    @PostMapping
    public ResponseEntity<ApiResponse<Cart>> createCart(@RequestBody Cart cart) {
        Cart saved = cartService.createCart(cart);
        ApiResponse<Cart> response = new ApiResponse<>("created", 200, saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cart>> updateCart(@PathVariable UUID id, @RequestBody Cart cart) {
        Cart updated = cartService.updateCart(id, cart);
        ApiResponse<Cart> response = new ApiResponse<>("updated", 200, updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable UUID id) {
        cartService.deleteCart(id);
        ApiResponse<Void> response = new ApiResponse<>("deleted", 200, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<Cart>> getActiveCartByCustomerId(@RequestParam int customerId) {
        Cart cart = cartService.getActiveCartByCustomer(customerId);
        ApiResponse<Cart> response = new ApiResponse<>("success", 200, cart);
        return ResponseEntity.ok(response);
    }
}

