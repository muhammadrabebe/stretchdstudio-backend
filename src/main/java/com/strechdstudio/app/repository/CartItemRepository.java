package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    // Get all items in a specific cart
    List<CartItem> findByCart_CartId(UUID cartId);

    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(UUID cartId, UUID productId);

}
