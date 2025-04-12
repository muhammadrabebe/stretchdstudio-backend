package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.OrderItemDTO;
import com.strechdstudio.app.model.OrderItem;
import com.strechdstudio.app.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderItemDTO>>> getAllOrderItems() {
        List<OrderItemDTO> items = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(new ApiResponse<>("success", 200, items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItem>> getOrderItem(@PathVariable UUID id) {
        OrderItem item = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", 200, item));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderItem>> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem created = orderItemService.createOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("created", 201, created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItem>> updateOrderItem(@PathVariable UUID id, @RequestBody OrderItem orderItem) {
        OrderItem updated = orderItemService.updateOrderItem(id, orderItem);
        return ResponseEntity.ok(new ApiResponse<>("updated", 200, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrderItem(@PathVariable UUID id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok(new ApiResponse<>("deleted", 204, null));
    }
}

