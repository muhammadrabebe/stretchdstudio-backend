package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.InstructorDTO;
import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Codelist;
import com.strechdstudio.app.model.Instructor;
import com.strechdstudio.app.repository.CodeLkupRepository;
import com.strechdstudio.app.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    @Autowired
    public InstructorRepository instructorRepository;
    @Autowired
    public CodeLkupRepository codeLkupRepository;

    // Get all Instructors
    public List<Instructor> getAllInstructors() {
        List<Instructor> Instructors = instructorRepository.findAll();
        return Instructors;
    }

    // Get Instructor by ID
    public Optional<Instructor> getInstructorByid(Integer instructorId) {
        return instructorRepository.findById(instructorId);
    }

    // Save a new Instructor
    public Instructor saveInstructor(InstructorDTO instructorDTO) {
        // Check if the instructor already exists
        Optional<Instructor> existingInstructor = Optional.ofNullable(instructorRepository.findByPhoneNumber(instructorDTO.getPhoneNumber()));
        if (existingInstructor.isPresent()) {
            throw new RuntimeException("Instructor " + instructorDTO.getFirstName() + " " + instructorDTO.getLastName() + " already exists.");
        }

        // Retrieve CodeLkup entities for status and specialization
        CodeLkup statusCodelkup = codeLkupRepository.findById(instructorDTO.getStatusid())
                .orElseThrow(() -> new RuntimeException("Status ID " + instructorDTO.getStatusid() + " does not exist."));

        CodeLkup specializationCodelkup = codeLkupRepository.findById(instructorDTO.getSpecializationid())
                .orElseThrow(() -> new RuntimeException("Specialization ID " + instructorDTO.getSpecializationid() + " does not exist."));

        // Create a new Instructor instance
        Instructor currentInstructor = new Instructor();
        LocalDateTime currentDate = LocalDateTime.now();

        // Map DTO properties to Instructor entity
        currentInstructor.setFirstName(instructorDTO.getFirstName());
        currentInstructor.setLastName(instructorDTO.getLastName());
        currentInstructor.setEmail(instructorDTO.getEmail());
        currentInstructor.setPhoneNumber(instructorDTO.getPhoneNumber());
        currentInstructor.setBio(instructorDTO.getBio());
        currentInstructor.setStatus(statusCodelkup);
        currentInstructor.setSpecialization(specializationCodelkup);
        currentInstructor.setAddWho(instructorDTO.getAddWho());
        currentInstructor.setAddDate(currentDate);
        currentInstructor.setEditWho(instructorDTO.getEditWho());
        currentInstructor.setEditDate(currentDate);

        // Save and return the instructor
        return instructorRepository.save(currentInstructor);
    }


    // Update an existing Instructor
    public Instructor updatedInstructor(Integer instructorId, Instructor updatedInstructor) {
        if (instructorRepository.existsById(instructorId)) {
            return instructorRepository.save(updatedInstructor);
        } else {
            throw new RuntimeException("Instructor not found with ID: " + instructorId);
        }
    }

    // Delete a Instructor by ID
    public void deleteInstructor(Integer instructorId) {
        if (instructorRepository.existsById(instructorId)) {
            instructorRepository.deleteById(instructorId);
        } else {
            throw new RuntimeException("Instructor not found with ID: " + instructorId);
        }
    }

}
