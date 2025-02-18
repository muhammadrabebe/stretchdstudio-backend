package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.Instructor;
import com.strechdstudio.app.service.ClassService;
import com.strechdstudio.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class ClassController {
     ClassService classService = new ClassService();

    // Constructor-based injection (Recommended)
    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    // Get all classes
    @GetMapping
    public ResponseEntity<ApiResponse<List<Class>>> getAllClasses() {
        List<Class> classList = classService.getAllClasses();
        ApiResponse<List<Class>> response = new ApiResponse<>("success", 200,classList);
        return ResponseEntity.ok(response);
    }
    // Get class by ID
    @GetMapping("/{classId}")
    public ResponseEntity<ApiResponse<Optional<Class> >> getClassById(@PathVariable Integer classId) {
        Optional<Class> classEntity = classService.getClassById(classId);
        ApiResponse<Optional<Class>> response = new ApiResponse<>("success", 200,classEntity);
        return ResponseEntity.ok(response);
    }

    // Create a new class
    @PostMapping
    public ResponseEntity<ApiResponse<Class>>  createClass(@RequestBody ClassDTO classDTO) {
        Class savedClass = classService.saveClass(classDTO);

        if (savedClass == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Failed to save instructor", 400, null));
        }

        ApiResponse<Class> response = new ApiResponse<>("Instructor saved successfully", 200, savedClass);
        return ResponseEntity.ok(response);
    }
}
