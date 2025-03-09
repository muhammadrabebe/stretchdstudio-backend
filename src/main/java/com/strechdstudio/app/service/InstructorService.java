package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.InstructorDTO;
import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Instructor;
import com.strechdstudio.app.repository.CodeLkupRepository;
import com.strechdstudio.app.repository.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorService {
    @Autowired
    public InstructorRepository instructorRepository;
    @Autowired
    public CodeLkupRepository codeLkupRepository;

    // Get all Instructors
    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll()  // Fetch all classes
                .stream()
                .map(InstructorDTO::new)  // Convert each Class to ClassDTO
                .collect(Collectors.toList()); // Collect as a List
    }

    public InstructorDTO getInstructorById(Integer instructorId) {
        // Validate instructor ID
        if (instructorId == null || instructorId <= 0) {
            throw new IllegalArgumentException("Invalid instructor ID. It must be a positive number.");
        }

        // Fetch instructor and handle not found scenario
        Instructor instructorEntity = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found"));

        // Return the mapped InstructorDTO
        return new InstructorDTO(instructorEntity);
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
        currentInstructor.setEditWho(instructorDTO.getAddWho());
        currentInstructor.setEditDate(currentDate);

        // Save and return the instructor
        return instructorRepository.save(currentInstructor);
    }


    // Update an existing Instructor
    public Instructor updateInstructor(Integer instructorId, InstructorDTO instructorDTO) {
        // Validate instructor ID
        if (instructorId == null || instructorId <= 0) {
            throw new IllegalArgumentException("Invalid instructor ID");
        }

        // Check if the instructor exists
        Instructor existingInstructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor with ID " + instructorId + " not found."));

        // Check if another instructor exists with the same phone number
        Optional<Instructor> instructorWithSamePhone = Optional.ofNullable(instructorRepository.findByPhoneNumber(instructorDTO.getPhoneNumber()));
        if (instructorWithSamePhone.isPresent() && !instructorWithSamePhone.get().getInstructorId().equals(instructorId)) {
            throw new RuntimeException("Instructor with phone number " + instructorDTO.getPhoneNumber() + " already exists.");
        }

        // Retrieve CodeLkup entities for status and specialization
        CodeLkup statusCodelkup = codeLkupRepository.findById(instructorDTO.getStatusid())
                .orElseThrow(() -> new RuntimeException("Status ID " + instructorDTO.getStatusid() + " does not exist."));

        CodeLkup specializationCodelkup = codeLkupRepository.findById(instructorDTO.getSpecializationid())
                .orElseThrow(() -> new RuntimeException("Specialization ID " + instructorDTO.getSpecializationid() + " does not exist."));

        // Update existing instructor entity with new values
        LocalDateTime currentDate = LocalDateTime.now();
        existingInstructor.setFirstName(instructorDTO.getFirstName());
        existingInstructor.setLastName(instructorDTO.getLastName());
        existingInstructor.setEmail(instructorDTO.getEmail());
        existingInstructor.setPhoneNumber(instructorDTO.getPhoneNumber());
        existingInstructor.setBio(instructorDTO.getBio());
        existingInstructor.setStatus(statusCodelkup);
        existingInstructor.setSpecialization(specializationCodelkup);
        existingInstructor.setEditWho(instructorDTO.getEditWho());
        existingInstructor.setEditDate(currentDate);

        // Save and return the updated instructor
        return instructorRepository.save(existingInstructor);
    }


    // Delete Instructor by ID
    public void deleteInstructor(Integer instructorId) {
        // Validate instructor ID
        if (instructorId == null || instructorId <= 0) {
            throw new IllegalArgumentException("Invalid instructor ID");
        }

        // Check if the instructor exists
        Instructor existingInstructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor with ID " + instructorId + " not found."));

        // Delete the instructor
        instructorRepository.delete(existingInstructor);
    }

    public Integer getActiveInstructorCount() {
        return instructorRepository.countActiveInstructors();
    }
}
