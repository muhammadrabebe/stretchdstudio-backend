package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.model.ClassType;
import com.strechdstudio.app.service.ClassTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classTypes")
public class ClassTypeController {

    private final ClassTypeService service;

    public ClassTypeController(ClassTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClassType>>> getAllClassTypes() {
        List<ClassType> classTypeList = service.getAllClassTypes();
        ApiResponse<List<ClassType>> response = new ApiResponse<>("success", 200, classTypeList);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClassType>> createClassType(@RequestBody ClassType classType) {
        ClassType savedClassType = service.createClassType(classType);
        ApiResponse<ClassType> response = new ApiResponse<>("Class type created successfully", 201, savedClassType);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<ApiResponse<ClassType>> updateClassType(@PathVariable UUID typeId, @RequestBody ClassType updatedClassType) {
        ClassType updated = service.updateClassType(typeId, updatedClassType);
        ApiResponse<ClassType> response = new ApiResponse<>("Class type updated successfully", 200, updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<ApiResponse<Void>> deleteClassType(@PathVariable UUID typeId) {
        service.deleteClassType(typeId);
        ApiResponse<Void> response = new ApiResponse<>("Class type deleted successfully", 200, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active/count")
    public ResponseEntity<ApiResponse<Integer>> getActiveInstructorCount() {
        Integer count = service.getActiveClassTypeCount();
        ApiResponse<Integer> response = new ApiResponse<>("success", 200, count);
        return ResponseEntity.ok(response);
    }

}