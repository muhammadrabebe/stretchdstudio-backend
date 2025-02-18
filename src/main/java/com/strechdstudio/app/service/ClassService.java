package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.InstructorDTO;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.Instructor;
import com.strechdstudio.app.repository.ClassRepository;
import com.strechdstudio.app.repository.CodeLkupRepository;
import com.strechdstudio.app.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    
    @Autowired
    public ClassRepository classRepository;

    @Autowired
    public InstructorRepository instructorRepository;

    @Autowired
    public CodeLkupRepository codeLkupRepository;

    // Get all classes
    public List<Class> getAllClasses() {
        List<Class> Classes = classRepository.findAll();
        return Classes;
    }

    // Get class by ID
    public Optional<Class> getClassById(Integer classId) {
        return classRepository.findById(classId);
    }

    // Save a new class
    public Class saveClass(ClassDTO classDTO) {
        // Check if the class already exists
        Optional<Class> existingClass = Optional.ofNullable(classRepository.findByClassName(classDTO.getClassName()));
        if (existingClass.isPresent()) {
            throw new RuntimeException("Class " + classDTO.getClassName() + " already exists.");
        }

        // Create a new class instance
        Class currentClass = new Class();
        LocalDateTime currentDate = LocalDateTime.now();

        // Fetch the related entities based on the provided IDs
        Instructor instructor = instructorRepository.findById(classDTO.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found for ID: " + classDTO.getInstructorId()));

        CodeLkup branch = codeLkupRepository.findById(classDTO.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found for ID: " + classDTO.getBranchId()));

        CodeLkup status = codeLkupRepository.findById(classDTO.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found for ID: " + classDTO.getStatusId()));

        // Map DTO properties to class entity
        currentClass.setClassName(classDTO.getClassName());
        currentClass.setStartTime(classDTO.getStartTime());
        currentClass.setEndTime(classDTO.getEndTime());
        currentClass.setMaxCapacity(classDTO.getMaxCapacity());
        currentClass.setLocation(classDTO.getLocation());
        currentClass.setAddressOne(classDTO.getAddressOne());
        currentClass.setAddressTwo(classDTO.getAddressTwo());
        currentClass.setAddressThree(classDTO.getAddressThree());
        currentClass.setCity(classDTO.getCity());
        currentClass.setBranch(branch); // Set the fetched branch entity
        currentClass.setStatus(status); // Set the fetched status entity
        currentClass.setInstructor(instructor); // Set the fetched instructor entity
        currentClass.setAddWho(classDTO.getAddWho());
        currentClass.setAddDate(currentDate);
        currentClass.setEditWho(classDTO.getEditWho());
        currentClass.setEditDate(currentDate);

        // Save and return the class
        return classRepository.save(currentClass);
    }


    // Update an existing class
    public Class updateClass(Integer classId, Class updatedClass) {
        if (classRepository.existsById(classId)) {
            return classRepository.save(updatedClass);
        } else {
            throw new RuntimeException("Class not found with ID: " + classId);
        }
    }

    // Delete a class by ID
    public void deleteClass(Integer classId) {
        if (classRepository.existsById(classId)) {
            classRepository.deleteById(classId);
        } else {
            throw new RuntimeException("Class not found with ID: " + classId);
        }
    }
}
