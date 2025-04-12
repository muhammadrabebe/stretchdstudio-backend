package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    // All items in a specific order
    List<OrderItem> findByOrder_OrderId(UUID orderId);
}

