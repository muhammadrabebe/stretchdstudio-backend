package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Example method to find customers by membership type
    List<Customer> findByMembershipType(CodeLkup membershipType);
}
