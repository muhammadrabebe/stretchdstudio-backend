package com.strechdstudio.app.service;

import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.repository.CodeLkupRepository;
import com.strechdstudio.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CodeLkupRepository codeLkupRepository;  // For managing statuses, membership types, etc.

    // Create a new customer
    public Customer createCustomer(Customer customer) {
        // You can add business logic here if needed before saving
        // For example: Validate email format, check for duplicate email, etc.
        return customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> myCustomers = customerRepository.findAll();
        return myCustomers;
    }

    // Get customer by ID
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    // Update an existing customer
    public Customer updateCustomer(Long customerId, Customer customer) {
        // Check if the customer exists
        Optional<Customer> existingCustomer = customerRepository.findById(customerId);
        if (existingCustomer.isPresent()) {
            Customer currentCustomer = existingCustomer.get();

            // Update fields here (you can add more logic like checking for changes)
            currentCustomer.setFirstName(customer.getFirstName());
            currentCustomer.setLastName(customer.getLastName());
            currentCustomer.setEmail(customer.getEmail());
            currentCustomer.setPhoneNumber(customer.getPhoneNumber());
            currentCustomer.setStatus(customer.getStatus());
            currentCustomer.setMembershipType(customer.getMembershipType());
            currentCustomer.setPreferredContactMethod(customer.getPreferredContactMethod());
            currentCustomer.setCity(customer.getCity());
            currentCustomer.setState(customer.getState());
            currentCustomer.setCountry(customer.getCountry());
            currentCustomer.setEditWho(customer.getEditWho());
            currentCustomer.setEditDate(customer.getEditDate());

            return customerRepository.save(currentCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + customerId);
        }
    }

    // Delete a customer
    public void deleteCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        } else {
            throw new RuntimeException("Customer not found with id " + customerId);
        }
    }

    // Example of method to get customers by membership type (can add more business logic)
    public List<Customer> getCustomersByMembershipType(String membershipTypeCode) {
        CodeLkup membershipType = codeLkupRepository.findByCode(membershipTypeCode); // Assuming 'membershipTypeCode' matches the code in codeLkup
        if (membershipType != null) {
            return customerRepository.findByMembershipType(membershipType);  // Assuming a method in the repository to find by membership type
        } else {
            throw new RuntimeException("Invalid membership type code: " + membershipTypeCode);
        }
    }
}
