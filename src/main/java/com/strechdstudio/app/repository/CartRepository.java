package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    Cart findByCustomer_CustomerId(int customerId);

    Cart findByCustomer_CustomerIdAndStatus_CodeLkupId(int customerId, int statusId);

}
