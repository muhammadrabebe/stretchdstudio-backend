package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.InstructorDTO;
import com.strechdstudio.app.model.Instructor;
import com.strechdstudio.app.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {


    public InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InstructorDTO>>> getALlInstructors(){
        List<InstructorDTO> instructorList = instructorService.getAllInstructors();
        ApiResponse<List<InstructorDTO>> response = new ApiResponse<>("success", 200,instructorList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<ApiResponse<InstructorDTO>> getInstructorById(@PathVariable Integer instructorId) {
        InstructorDTO instructorDTO = instructorService.getInstructorById(instructorId);
        ApiResponse<InstructorDTO> response = new ApiResponse<>("success", 200, instructorDTO);
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

    @PutMapping("/{instructorId}")
    public ResponseEntity<ApiResponse<Instructor>> updateInstructor(
            @PathVariable Integer instructorId,
            @RequestBody InstructorDTO instructorDTO) {

        Instructor updatedInstructor = instructorService.updateInstructor(instructorId, instructorDTO);
        ApiResponse<Instructor> response = new ApiResponse<>("Instructor updated successfully", 200, updatedInstructor);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{instructorId}")
    public ResponseEntity<ApiResponse<String>> deleteInstructor(@PathVariable Integer instructorId) {
        instructorService.deleteInstructor(instructorId);
        ApiResponse<String> response = new ApiResponse<>("Instructor deleted successfully", 200, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active/count")
    public ResponseEntity<ApiResponse<Integer>> getActiveInstructorCount() {
        Integer count = instructorService.getActiveInstructorCount();
        ApiResponse<Integer> response = new ApiResponse<>("success", 200, count);
        return ResponseEntity.ok(response);
    }
}
