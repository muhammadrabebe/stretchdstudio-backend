package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.OrderItemDTO;
import com.strechdstudio.app.model.OrderItem;
import com.strechdstudio.app.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAll().stream().map(item -> {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setOrderItemId(item.getOrderItemId());
            dto.setOrderId(item.getOrder().getOrderId());
            dto.setProductId(item.getProduct().getProductId());
            dto.setQuantity(item.getQuantity());
            dto.setPriceAtPurchase(item.getPriceAtPurchase());
            dto.setAddDate(item.getAddDate());
            dto.setAddWho(item.getAddWho());
            dto.setEditDate(item.getEditDate());
            dto.setEditWho(item.getEditWho());
            return dto;
        }).collect(Collectors.toList());
    }

    public OrderItem getOrderItemById(UUID id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order item not found"));
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        orderItem.setAddDate(LocalDateTime.now());
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(UUID id, OrderItem updatedItem) {
        OrderItem existing = getOrderItemById(id);
        updatedItem.setOrderItemId(existing.getOrderItemId());
        updatedItem.setAddDate(existing.getAddDate());
        updatedItem.setEditDate(LocalDateTime.now());
        return orderItemRepository.save(updatedItem);
    }

    public void deleteOrderItem(UUID id) {
        if (!orderItemRepository.existsById(id)) {
            throw new NoSuchElementException("Order item not found");
        }
        orderItemRepository.deleteById(id);
    }
}
