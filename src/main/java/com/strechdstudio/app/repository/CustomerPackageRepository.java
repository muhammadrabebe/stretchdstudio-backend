package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.ClassPackage;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.CustomerPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CustomerPackageRepository extends JpaRepository<CustomerPackage, UUID> {
    CustomerPackage findByCustomerAndClassPackage(Customer customer, ClassPackage classPackage);

    List<CustomerPackage> findByCustomer_CustomerIdOrderByPurchaseDateDesc(Integer customerId);

    CustomerPackage findTopByCustomer_CustomerIdAndClassPackage_ExpiryDateAfterOrderByPurchaseDateDesc(
            Integer customerId, LocalDateTime currentDate);

}
