package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.OrderDTO;
import com.strechdstudio.app.model.*;
import com.strechdstudio.app.repository.*;
import com.strechdstudio.app.util.DateTimeUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CodeLkupRepository codeLkupRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderNumber(order.getOrderNumber());
            dto.setCustomerName(order.getCustomer().getFullname());
            dto.setCustomerId(order.getCustomer().getCustomerId());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setStatus(order.getStatus().getCode());
            dto.setStatusId(order.getStatus().getCodeLkupId());
            dto.setAddDate(DateTimeUtils.formatDateTime(order.getAddDate()));
            dto.setAddWho(order.getAddWho());
            dto.setEditDate(order.getEditDate());
            dto.setEditWho(order.getEditWho());
            return dto;
        }).collect(Collectors.toList());
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
    }

    public Order createOrder(Order order) {
        order.setAddDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrder(UUID id, Order updatedOrder) {
        Order existing = getOrderById(id);
        updatedOrder.setOrderId(existing.getOrderId());
        updatedOrder.setAddDate(existing.getAddDate());
        updatedOrder.setEditDate(LocalDateTime.now());
        return orderRepository.save(updatedOrder);
    }

    public Order updateOrderStatus(UUID id, String newStatus) {
        // Fetch the new status CodeLkup
        CodeLkup orderStatus = codeLkupRepository.findByCodelist_ListNameAndCode("ORDERSTATUS", newStatus)
                .orElseThrow(() -> new EntityNotFoundException(newStatus + " status not found"));

        // Get current order
        Order order = getOrderById(id);
        String currentStatus = order.getStatus().getCode();

        // Define allowed transitions
        if (currentStatus.equalsIgnoreCase("pending")) {
            if (!newStatus.equalsIgnoreCase("paid")) {
                throw new RuntimeException("Invalid status transition: Pending can only be updated to Paid.");
            }
        } else if (currentStatus.equalsIgnoreCase("paid")) {
            if(newStatus.equalsIgnoreCase("paid"))
                throw new RuntimeException("The order is already paid.");
            else if (!newStatus.equalsIgnoreCase("cancelled"))
                throw new RuntimeException("The order is paid, you can't change it.");
        } else {
            throw new RuntimeException("Status update not allowed from status: " + currentStatus);
        }

        // Update and save
        order.setStatus(orderStatus);
        order.setEditDate(LocalDateTime.now());
        return orderRepository.save(order);
    }


    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new NoSuchElementException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    public Order checkoutCart(UUID cartId, int customerId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        List<CartItem> items = cartItemRepository.findByCart_CartId(cartId);
        if (items.isEmpty()) throw new RuntimeException("Cart is empty");

        BigDecimal total = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CodeLkup orderStatus = codeLkupRepository.findByCodelist_ListNameAndCode("ORDERSTATUS", "Pending")
                .orElseThrow(() -> new EntityNotFoundException("Pending Status not found"));

        CodeLkup cartStatus = codeLkupRepository.findByCodelist_ListNameAndCode("CARTSTATUS", "Checked Out")
                .orElseThrow(() -> new EntityNotFoundException("Checked Out Status not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        String orderNumber = "ORD-" + LocalDate.now() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderNumber(orderNumber);
        order.setTotalAmount(total);
        order.setStatus(orderStatus); // You can use codeLkup's ID for 'pending'
        order.setAddDate(LocalDateTime.now());
        order.setAddWho(customer.getFullname());
        order.setEditDate(LocalDateTime.now());
        order.setEditWho(customer.getFullname());
        order = orderRepository.save(order);

        for (CartItem item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(item.getProduct().getPrice());
            orderItem.setAddDate(LocalDateTime.now());
            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(items); // Clear cart
        cart.setStatus(cartStatus); // codeLkup status like 'checked_out'
        cartRepository.save(cart);

        return null;
    }

}
