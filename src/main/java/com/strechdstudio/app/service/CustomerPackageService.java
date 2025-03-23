package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.CustomerPackageDTO;
import com.strechdstudio.app.model.ClassPackage;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.CustomerPackage;
import com.strechdstudio.app.repository.ClassPackageRepository;
import com.strechdstudio.app.repository.CustomerPackageRepository;
import com.strechdstudio.app.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerPackageService {
    @Autowired
    private CustomerPackageRepository customerPackageRepository;

    @Autowired
    private ClassPackageRepository classPackageRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerPackageDTO> getAllCustomerPackages() {
        return customerPackageRepository.findAll()
                .stream()
                .map(customerPackage -> {
                    CustomerPackageDTO dto = new CustomerPackageDTO();
                    dto.setCustomerPackageId(customerPackage.getCustomerPackageId());
                    dto.setCustomerId(customerPackage.getCustomer().getCustomerId());
                    dto.setCustomerName(customerPackage.getCustomer().getFirstName() + " " + customerPackage.getCustomer().getLastName());
                    dto.setClassPackageId(customerPackage.getClassPackage().getclassPackageId());
                    dto.setClassPackageName(customerPackage.getClassPackage().getName());
                    dto.setPurchaseDate(customerPackage.getPurchaseDate());
                    dto.setRemainingClasses(customerPackage.getRemainingClasses());
                    // Set other DTO properties as needed
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public CustomerPackage getCustomerPackageById(UUID id) {
        return customerPackageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer Package not found"));
    }

    public CustomerPackage purchaseClassPackage(Integer customerId, UUID classPackageId) {
        // Fetch the related entities based on the provided IDs
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ClassPackage classPackage = classPackageRepository.findById(classPackageId)
                .orElseThrow(() -> new RuntimeException("classPackage not found"));

        // Validate if the customer has already purchased this package
        CustomerPackage existingPurchase = customerPackageRepository.findByCustomerAndClassPackage(customer, classPackage);
        if (existingPurchase != null)
            throw new RuntimeException("You already bought this package.");

        // Create a new customer package instance
        CustomerPackage currentCustomerPackage = new CustomerPackage();
        LocalDateTime currentDate = LocalDateTime.now();

        // Map DTO properties to customer package entity
        currentCustomerPackage.setCustomer(customer); // Set the fetched customer entity
        currentCustomerPackage.setClassPackage(classPackage); // Set the fetched class package entity
        currentCustomerPackage.setRemainingClasses(classPackage.getTotalClasses());
        currentCustomerPackage.setPurchaseDate(currentDate);

        // Save and return the customer package
        return customerPackageRepository.save(currentCustomerPackage);
    }

    public void deleteCustomerPackage(UUID id) {
        if (!customerPackageRepository.existsById(id)) {
            throw new NoSuchElementException("Customer Package not found");
        }
        customerPackageRepository.deleteById(id);
    }

    public List<CustomerPackageDTO> getCustomerPackagesByCustomer(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer with ID " + customerId + " not found"));

        return customerPackageRepository.findByCustomer_CustomerIdOrderByPurchaseDateDesc(customerId)
                .stream()
                .map(customerPackage -> {
                    CustomerPackageDTO dto = new CustomerPackageDTO();
                    dto.setCustomerPackageId(customerPackage.getCustomerPackageId());
                    dto.setCustomerId(customerPackage.getCustomer().getCustomerId());
                    dto.setCustomerName(customerPackage.getCustomer().getFirstName() + " " + customerPackage.getCustomer().getLastName());
                    dto.setClassPackageId(customerPackage.getClassPackage().getclassPackageId());
                    dto.setClassPackageName(customerPackage.getClassPackage().getName());
                    dto.setPurchaseDate(customerPackage.getPurchaseDate());
                    dto.setRemainingClasses(customerPackage.getRemainingClasses());
                    dto.setTotalClasses(customerPackage.getClassPackage().getTotalClasses());
                    dto.setStartDate(customerPackage.getClassPackage().getStartDate());
                    dto.setExpiryDate(customerPackage.getClassPackage().getExpiryDate());
                    dto.setPrice(customerPackage.getClassPackage().getPrice());
                    dto.setIsExpired(customerPackage.getClassPackage().getExpiryDate().isBefore(LocalDateTime.now()));

                    // Add other properties if needed
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public CustomerPackageDTO getNewestValidCustomerPackage(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer with ID " + customerId + " not found"));

        CustomerPackage customerPackage = customerPackageRepository
                .findTopByCustomer_CustomerIdAndClassPackage_ExpiryDateAfterOrderByPurchaseDateDesc(customerId, LocalDateTime.now());

        if (customerPackage == null)
//            throw new RuntimeException("No valid (non-expired) customer package found for customer ID " + customerId);
            return null;

        CustomerPackageDTO dto = new CustomerPackageDTO();
        dto.setCustomerPackageId(customerPackage.getCustomerPackageId());
        dto.setCustomerId(customerPackage.getCustomer().getCustomerId());
        dto.setCustomerName(customerPackage.getCustomer().getFirstName() + " " + customerPackage.getCustomer().getLastName());
        dto.setClassPackageId(customerPackage.getClassPackage().getclassPackageId());
        dto.setClassPackageName(customerPackage.getClassPackage().getName());
        dto.setPurchaseDate(customerPackage.getPurchaseDate());
        dto.setRemainingClasses(customerPackage.getRemainingClasses());
        dto.setTotalClasses(customerPackage.getClassPackage().getTotalClasses());
        dto.setStartDate(customerPackage.getClassPackage().getStartDate());
        dto.setExpiryDate(customerPackage.getClassPackage().getExpiryDate());
        dto.setPrice(customerPackage.getClassPackage().getPrice());
        dto.setIsExpired(customerPackage.getClassPackage().getExpiryDate().isBefore(LocalDateTime.now()));

        return dto;
    }


}
