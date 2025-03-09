package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<ApiResponse<List<ClassDTO>>> getAllClasses() {
        List<ClassDTO> classList = classService.getAllClasses();
        ApiResponse<List<ClassDTO>> response = new ApiResponse<>("success", 200, classList);
        return ResponseEntity.ok(response);
    }
    // Get class by ID
    @GetMapping("/{classId}")
    public ResponseEntity<ApiResponse<ClassDTO>> getClassById(@PathVariable Integer classId) {
        ClassDTO classEntity = classService.getClassById(classId);
        ApiResponse<ClassDTO> response = new ApiResponse<>("success", 200, classEntity);
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

    // update a class
    @PutMapping("/{classId}")
    public ResponseEntity<ApiResponse<ClassDTO>> updateClass(
            @PathVariable Integer classId,
            @RequestBody ClassDTO classDTO) {

        ClassDTO updatedClass = classService.updateClass(classId, classDTO);
        ApiResponse<ClassDTO> response = new ApiResponse<>("Class updated successfully", 200, updatedClass);
        return ResponseEntity.ok(response);
    }

    // delete a class
    @DeleteMapping("/{classId}")
    public ResponseEntity<ApiResponse<Void>> deleteClass(@PathVariable Integer classId) {
        classService.deleteClass(classId);
        ApiResponse<Void> response = new ApiResponse<>("Class deleted successfully", 200, null);
        return ResponseEntity.ok(response);
    }

    // find a class by instructor
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<ApiResponse<List<ClassDTO>>> getClassesByInstructorId(@PathVariable Integer instructorId) {
        List<ClassDTO> classes = classService.getClassesByInstructorId(instructorId);
        ApiResponse<List<ClassDTO>> response = new ApiResponse<>("success", 200, classes);
        return ResponseEntity.ok(response);
    }

    // find classes by type
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<ClassDTO>>> getClassesByType(@PathVariable String type) {
        List<ClassDTO> classList = classService.getClassesByType(type);
        ApiResponse<List<ClassDTO>> response = new ApiResponse<>("success", 200, classList);
        return ResponseEntity.ok(response);
    }

}
