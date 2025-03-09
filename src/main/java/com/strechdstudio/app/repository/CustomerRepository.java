package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Example method to find customers by membership type
    List<Customer> findByMembershipType(CodeLkup membershipType);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

}
