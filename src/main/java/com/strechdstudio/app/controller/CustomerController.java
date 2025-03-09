package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.CustomerDTO;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> customerDTOs = customerService.getAllCustomers();

        ApiResponse<List<CustomerDTO>> response = new ApiResponse<>("Customers fetched successfully", 200, customerDTOs);
        return ResponseEntity.ok(response);
    }

    // Get customer by ID
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerById(@PathVariable Integer customerId) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        ApiResponse<CustomerDTO> response = new ApiResponse<>("success", 200, customerDTO);
        return ResponseEntity.ok(response);
    }

    // Create a new customer
    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer savedCustomer = customerService.saveCustomer(customerDTO);

        if (savedCustomer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Failed to save customer", 400, null));
        }

        ApiResponse<Customer> response = new ApiResponse<>("Customer saved successfully", 200, savedCustomer);
        return ResponseEntity.ok(response);
    }

    // Update an existing customer
    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(@PathVariable Integer customerId,@RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(customerId, customerDTO);
        ApiResponse<CustomerDTO> response = new ApiResponse<>("Customer updated successfully", 200, updatedCustomer);
        return ResponseEntity.ok(response);
    }

    // Delete a customer
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Integer customerId) {
        try {
            customerService.deleteCustomer(customerId);
            ApiResponse<Void> response = new ApiResponse<>("Customer deleted successfully", 200, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>("Failed to delete customer: " + e.getMessage(), 400, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

//    @GetMapping("/active/count")
//    public ResponseEntity<ApiResponse<Integer>> getActiveInstructorCount() {
//        Integer count = customerService.getActiveInstructorCount();
//        ApiResponse<Integer> response = new ApiResponse<>("success", 200, count);
//        return ResponseEntity.ok(response);
//    }
}
