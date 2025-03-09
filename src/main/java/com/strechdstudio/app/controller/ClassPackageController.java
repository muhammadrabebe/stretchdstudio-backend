package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.ClassPackageDTO;
import com.strechdstudio.app.model.ClassPackage;
import com.strechdstudio.app.service.ClassPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classPackages")
public class ClassPackageController {
    @Autowired
    private ClassPackageService classPackageService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClassPackageDTO>>> getAllClassPackages() {
        List<ClassPackageDTO> classPackages = classPackageService.getAllClassPackages();
        return ResponseEntity.ok(new ApiResponse<>("Class packages retrieved successfully", 200, classPackages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassPackage>> getClassPackageById(@PathVariable UUID id) {
        ClassPackage classPackage = classPackageService.getClassPackageById(id);
        return ResponseEntity.ok(new ApiResponse<>("Class package retrieved successfully", 200, classPackage));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClassPackage>> createClassPackage(@RequestBody ClassPackage classPackage) {
        ClassPackage createdPackage = classPackageService.createClassPackage(classPackage);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Class package created successfully", 201, createdPackage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassPackage>> updateClassPackage(
            @PathVariable UUID id, @RequestBody ClassPackage classPackage) {
        ClassPackage updatedPackage = classPackageService.updateClassPackage(id, classPackage);
        return ResponseEntity.ok(new ApiResponse<>("Class package updated successfully", 200, updatedPackage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClassPackage(@PathVariable UUID id) {
        classPackageService.deleteClassPackage(id);
        return ResponseEntity.ok(new ApiResponse<>("Class package deleted successfully", 200, null));
    }
}


