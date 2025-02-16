package com.strechdstudio.app.controller;

import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    CustomerService customerService = new CustomerService();

    // Constructor-based injection (Recommended)
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get customer by ID
    @GetMapping("/{customerId}")
    public Optional<Customer> getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    // Create a new customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // Update an existing customer
    @PutMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
        return customerService.updateCustomer(customerId, customer);
    }

    // Delete a customer
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }
}
