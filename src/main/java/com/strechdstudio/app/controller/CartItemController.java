package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.CartItemDTO;
import com.strechdstudio.app.model.CartItem;
import com.strechdstudio.app.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    CartItemService cartItemService = new CartItemService();

    // Constructor-based injection (Recommended)
    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> getAllCartItems() {
        List<CartItemDTO> items = cartItemService.getAllCartItems();
        return ResponseEntity.ok(new ApiResponse<>("success", 200, items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartItem>> getCartItem(@PathVariable UUID id) {
        CartItem item = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", 200, item));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartItem>> createCartItem(@RequestBody CartItem cartItem) {
        CartItem created = cartItemService.createCartItem(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("created", 201, created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CartItem>> updateCartItem(@PathVariable UUID id, @RequestBody CartItem cartItem) {
        CartItem updated = cartItemService.updateCartItem(id, cartItem);
        return ResponseEntity.ok(new ApiResponse<>("updated", 200, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable UUID id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok(new ApiResponse<>("Item has been deleted successfully.", 204, null));
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @RequestParam UUID cartId,
            @RequestParam UUID productId,
            @RequestParam int quantity
    ) {
        CartItem item = cartItemService.addProductToCartWithStockCheck(cartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> getItemsByCart(@PathVariable int customerId) {
        List<CartItemDTO> cartItemDTOList = cartItemService.getItemsByCustomerActiveCart(customerId);
        ApiResponse<List<CartItemDTO>> response = new ApiResponse<>("success", 200, cartItemDTOList);
        return ResponseEntity.ok(response);
    }

}


