package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Find all orders by customer
    List<Order> findByCustomer_CustomerId(int customerId);

    // Find all orders with a specific status (like 'pending' or 'completed')
//    List<Order> findByStatusId(int statusId);
    List<Order> findByStatus_CodeLkupId(int statusId);

    // Order history for a customer, newest first
    List<Order> findByCustomer_CustomerId_OrderByAddDateDesc(int customerId);

}

