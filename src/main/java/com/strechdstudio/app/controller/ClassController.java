package com.strechdstudio.app.controller;

import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.service.ClassService;
import com.strechdstudio.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Class> getAllClasses() {
        return classService.getAllClasses();
    }
    // Get class by ID
    @GetMapping("/{classId}")
    public Optional<Class> getCustomerById(@PathVariable Integer classId) {
        return classService.getClassById(classId);
    }

    // Create a new class
    @PostMapping
    public Class createClass(@RequestBody Class classEntity) {
        return classService.saveClass(classEntity);
    }
}
