package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.ClassPackageDTO;
import com.strechdstudio.app.dto.CustomerPackageDTO;
import com.strechdstudio.app.model.ClassPackage;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.CustomerPackage;
import com.strechdstudio.app.repository.ClassPackageRepository;
import com.strechdstudio.app.repository.CustomerPackageRepository;
import com.strechdstudio.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        return customerPackageRepository.findAll()  // Fetch all classes
                .stream()
                .map(CustomerPackageDTO::new)  // Convert each Class to ClassDTO
                .collect(Collectors.toList()); // Collect as a List
    }

    public CustomerPackage getCustomerPackageById(UUID id) {
        return customerPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Package not found"));
    }

    public CustomerPackage purchaseClassPackage(Integer customerId, UUID classPackageId) {
        ClassPackage classPackage = classPackageRepository.findById(classPackageId)
                .orElseThrow(() -> new RuntimeException("Class Package not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerPackage customerPackage = new CustomerPackage();
        customerPackage.setCustomer(customer);
        customerPackage.setClassPackage(classPackage);
        customerPackage.setRemainingClasses(classPackage.getTotalClasses());
        customerPackage.setPurchaseDate(LocalDateTime.now());

        return customerPackageRepository.save(customerPackage);
    }

    public void deleteCustomerPackage(UUID id) {
        if (!customerPackageRepository.existsById(id)) {
            throw new RuntimeException("Customer Package not found");
        }
        customerPackageRepository.deleteById(id);
    }
}
