package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.CustomerDTO;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.repository.CodeLkupRepository;
import com.strechdstudio.app.repository.CustomerRepository;
import com.strechdstudio.app.util.EmailValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CodeLkupRepository codeLkupRepository;  // For managing statuses, membership types, etc.


    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        // Convert the list of Customer entities to CustomerDTO
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customer -> {
                    CustomerDTO dto = new CustomerDTO();
                    dto.setCustomerId(customer.getCustomerId());
                    dto.setFirstName(customer.getFirstName());
                    dto.setLastName(customer.getLastName());
                    dto.setEmail(customer.getEmail());
                    dto.setPhoneNumber(customer.getPhoneNumber());
                    dto.setStatusId(customer.getStatus().getCodeLkupId());
                    dto.setStatus(customer.getStatus().getCode());
                    dto.setMembershipType(customer.getMembershipType().getCode());
                    dto.setMembershipTypeId(customer.getMembershipType().getCodeLkupId());
                    dto.setPreferredContactMethod(customer.getPreferredContactMethod());
                    dto.setRegistrationDate(customer.getRegistrationDate());
                    dto.setTotalClassesAttended(customer.getTotalClassesAttended());
                    dto.setCity(customer.getCity());
                    dto.setState(customer.getState());
                    dto.setCountry(customer.getCountry());
                    dto.setAddWho(customer.getAddWho());
                    dto.setAddDate(customer.getAddDate());
                    dto.setEditWho(customer.getEditWho());
                    dto.setEditDate(customer.getEditDate());
                    // Set other DTO properties as needed
                    return dto;
                })
                .collect(Collectors.toList());

        return customerDTOs;
    }

    // Get customer by ID with validations
    public CustomerDTO getCustomerById(Integer customerId) {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Invalid class ID");
        }

        return customerRepository.findById(customerId)
                .map(CustomerDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + customerId + " not found"));
    }

    // Create a new customer
    public Customer saveCustomer(CustomerDTO customerDTO) throws IllegalArgumentException {
        // Check if the customer already exists by email
        Optional<Optional<Customer>> existingCustomerByEmail = Optional.ofNullable(customerRepository.findByEmail(customerDTO.getEmail()));
        if (existingCustomerByEmail.isEmpty()) {
            throw new RuntimeException("Customer with email " + customerDTO.getEmail() + " already exists.");
        }

        // Check if the customer already exists by phone number
        Optional<Optional<Customer>> existingCustomerByPhone = Optional.ofNullable(customerRepository.findByPhoneNumber(customerDTO.getPhoneNumber()));
        if (existingCustomerByPhone.isEmpty()) {
            throw new RuntimeException("Customer with phone number " + customerDTO.getPhoneNumber() + " already exists.");
        }

        // Create a new customer instance
        Customer customer = new Customer();
        LocalDateTime currentDate = LocalDateTime.now();

        // Fetch the related entities based on the provided IDs
        CodeLkup status = codeLkupRepository.findById(customerDTO.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found for ID: " + customerDTO.getStatusId()));

        CodeLkup membershipType = codeLkupRepository.findById(customerDTO.getMembershipTypeId())
                .orElseThrow(() -> new RuntimeException("Membership type not found for ID: " + customerDTO.getMembershipTypeId()));

        // Map DTO properties to customer entity
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setStatus(status); // Set the fetched status entity
        customer.setMembershipType(membershipType); // Set the fetched membership type entity
        customer.setPreferredContactMethod(customerDTO.getPreferredContactMethod());
        customer.setCity(customerDTO.getCity());
        customer.setState(customerDTO.getState());
        customer.setCountry(customerDTO.getCountry());
        customer.setRegistrationDate(currentDate);
        customer.setTotalClassesAttended(0);
        customer.setAddWho(customerDTO.getAddWho());
        customer.setAddDate(currentDate);
        customer.setEditWho(customerDTO.getAddWho());
        customer.setEditDate(currentDate);

        // Save and return the customer
        return customerRepository.save(customer);
    }

    // Update an existing customer
    public CustomerDTO updateCustomer(Integer customerId, CustomerDTO customerDTO) {

        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Invalid class ID");
        }

        Customer customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Class with ID " + customerId + " not found"));
        // Check if the customer exists

        LocalDateTime currentDate = LocalDateTime.now();
        // Update fields here (you can add more logic like checking for changes)
        customerEntity.setFirstName(customerDTO.getFirstName());
        customerEntity.setLastName(customerDTO.getLastName());
        customerEntity.setEmail(customerDTO.getEmail());
        customerEntity.setPhoneNumber(customerDTO.getPhoneNumber());
        customerEntity.setPreferredContactMethod(customerDTO.getPreferredContactMethod());
        customerEntity.setRegistrationDate(customerDTO.getRegistrationDate());
        customerEntity.setTotalClassesAttended(customerDTO.getTotalClassesAttended());
        customerEntity.setCity(customerDTO.getCity());
        customerEntity.setState(customerDTO.getState());
        customerEntity.setCountry(customerDTO.getCountry());
        customerEntity.setCountry(customerDTO.getCountry());
        customerEntity.setEditWho(customerDTO.getEditWho());
        customerEntity.setEditDate(currentDate);

        if (customerDTO.getMembershipTypeId() != null) {
            CodeLkup membershipType = codeLkupRepository.findById(customerDTO.getMembershipTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("Membership not found"));
            customerEntity.setMembershipType(membershipType);
        }

        if (customerDTO.getStatusId() != null) {
            CodeLkup status = codeLkupRepository.findById(customerDTO.getStatusId())
                    .orElseThrow(() -> new EntityNotFoundException("Status not found"));
            customerEntity.setStatus(status);
        }
        Customer updatedEntity = customerRepository.save(customerEntity);
        return new CustomerDTO(updatedEntity);
    }

    // Delete a customer
    public void deleteCustomer(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        } else {
            throw new RuntimeException("Customer not found with id " + customerId);
        }
    }

}
