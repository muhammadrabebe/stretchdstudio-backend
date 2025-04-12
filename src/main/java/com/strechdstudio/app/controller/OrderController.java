package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.CheckoutRequest;
import com.strechdstudio.app.dto.OrderDTO;
import com.strechdstudio.app.model.Order;
import com.strechdstudio.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/orders")
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse<>("success", 200, orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrder(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", 200, order));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody CheckoutRequest checkoutRequest) {
        Order created = orderService.checkoutCart(checkoutRequest.getCartId(),checkoutRequest.getCustomerId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Your order has been placed successfully.", 200, created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable UUID id, @RequestBody Order order) {
        Order updated = orderService.updateOrder(id, order);
        return ResponseEntity.ok(new ApiResponse<>("updated", 200, updated));
    }

    @PutMapping("/updateStatus/{orderId}/{status}")
    public ResponseEntity<ApiResponse<Order>> updateStatus(@PathVariable UUID orderId, @PathVariable String status) {
        Order updated = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(new ApiResponse<>("Order status has been updated to " + status, 200, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(new ApiResponse<>("deleted", 200, null));
    }
}


