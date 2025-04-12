package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.CustomerPackageDTO;
import com.strechdstudio.app.model.CustomerPackage;
import com.strechdstudio.app.service.CustomerPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customerPackages")
public class CustomerPackageController {
    @Autowired
    private CustomerPackageService customerPackageService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerPackageDTO>>> getAllCustomerPackages() {
        List<CustomerPackageDTO> customerPackages = customerPackageService.getAllCustomerPackages();
        return ResponseEntity.ok(new ApiResponse<>("Customer packages retrieved successfully", 200, customerPackages));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerPackageDTO>>> getCustomerPackagesByCustomer(@PathVariable Integer customerId) {
        List<CustomerPackageDTO> customerPackages = customerPackageService.getCustomerPackagesByCustomer(customerId);
        return ResponseEntity.ok(new ApiResponse<>("Customer packages retrieved successfully", 200, customerPackages));
    }

    @GetMapping("/customer/{customerId}/latest-valid-package")
    public ResponseEntity<ApiResponse<CustomerPackageDTO>> getNewestValidCustomerPackage(@PathVariable Integer customerId) {
        CustomerPackageDTO customerPackage = customerPackageService.getNewestValidCustomerPackage(customerId);
        return ResponseEntity.ok(new ApiResponse<>("Latest valid customer package retrieved successfully", 200, customerPackage));
    }


//    @PostMapping("/purchase/{customerId}/{classPackageId}")
//    public ResponseEntity<ApiResponse<CustomerPackage>> purchaseClassPackage(
//            @PathVariable Integer customerId, @PathVariable UUID classPackageId) {
//        CustomerPackage purchasedPackage = customerPackageService.purchaseClassPackage(customerId, classPackageId);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new ApiResponse<>("Class package purchased successfully", 201, purchasedPackage));
//    }

    @PostMapping("/purchase/{customerId}/{packageId}")
    public ResponseEntity<ApiResponse<String>> purchasePackage(@PathVariable Integer customerId, @PathVariable UUID packageId) {
        customerPackageService.purchaseClassPackage(customerId, packageId);
        return ResponseEntity.ok(new ApiResponse<>("Package purchased successfully", 200, null));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomerPackage(@PathVariable UUID id) {
        customerPackageService.deleteCustomerPackage(id);
        return ResponseEntity.ok(new ApiResponse<>("Customer package deleted successfully", 200, null));
    }
}


