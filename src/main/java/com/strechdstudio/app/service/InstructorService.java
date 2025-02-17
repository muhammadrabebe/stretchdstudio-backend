package com.strechdstudio.app.service;

import com.strechdstudio.app.model.Instructor;
import com.strechdstudio.app.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    @Autowired
    public InstructorRepository instructorRepository;

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
    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
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
