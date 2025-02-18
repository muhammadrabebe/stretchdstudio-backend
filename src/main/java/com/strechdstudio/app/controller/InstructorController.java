package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.InstructorDTO;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.Instructor;
import com.strechdstudio.app.repository.InstructorRepository;
import com.strechdstudio.app.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instructors")
public class InstructorController {


    public InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Instructor>>> getALlInstructors(){
        List<Instructor> instructorList = instructorService.getAllInstructors();
        ApiResponse<List<Instructor>> response = new ApiResponse<>("success", 200,instructorList);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Instructor>> saveInstructor(@RequestBody InstructorDTO instructor) {
        Instructor savedInstructor = instructorService.saveInstructor(instructor);

        if (savedInstructor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Failed to save instructor", 400, null));
        }

        ApiResponse<Instructor> response = new ApiResponse<>("Instructor saved successfully", 200, savedInstructor);
        return ResponseEntity.ok(response);
    }
}
