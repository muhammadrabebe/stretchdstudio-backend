package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.ClassPackage;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.CustomerPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerPackageRepository extends JpaRepository<CustomerPackage, UUID> {
    CustomerPackage findByCustomerAndClassPackage(Customer customer, ClassPackage classPackage);

    List<CustomerPackage> findByCustomer_CustomerIdOrderByPurchaseDateDesc(Integer customerId);

    CustomerPackage findTopByCustomer_CustomerIdAndClassPackage_ExpiryDateAfterOrderByPurchaseDateDesc(
            Integer customerId, LocalDateTime currentDate);

//    Optional<CustomerPackage> findByCustomerAndClassPackage_ClassType(Customer customer, ClassType classType);

    @Query("""
    SELECT cp FROM CustomerPackage cp
    WHERE cp.customer.customerId = :customerId
      AND cp.status.codeLkupId = :statusId
      AND cp.classPackage.classType.typeId = :typeId
""")
    CustomerPackage findByCustomerAndClassTypeAndStatusId(
            @Param("customerId") int customerId,
            @Param("typeId") UUID classTypeId,
            @Param("statusId") int statusId
    );


}
