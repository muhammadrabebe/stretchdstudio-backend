package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.InstructorDTO;
import com.strechdstudio.app.model.*;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.repository.ClassRepository;
import com.strechdstudio.app.repository.ClassTypeRepository;
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
public class ClassService {
    
    @Autowired
    public ClassRepository classRepository;

    @Autowired
    public InstructorRepository instructorRepository;

    @Autowired
    public CodeLkupRepository codeLkupRepository;

    @Autowired
    public ClassTypeRepository classTypeRepository;

    public List<ClassDTO> getAllClasses() {
        return classRepository.findAll()  // Fetch all classes
                .stream()
                .map(ClassDTO::new)  // Convert each Class to ClassDTO
                .collect(Collectors.toList()); // Collect as a List
    }

    public List<ClassDTO> getClassesByDate(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return classRepository.findByStartTimeBetweenOrderByStartTimeAsc(startOfDay,endOfDay)  // Fetch all classes
                .stream()
                .map(ClassDTO::new)  // Convert each Class to ClassDTO
                .collect(Collectors.toList()); // Collect as a List
    }

    public List<ClassDTO> getClassesByDateAndByType(LocalDateTime startOfDay, LocalDateTime endOfDay, String type) {
        ClassType classType = classTypeRepository.findByType(type);

        return classRepository.findByStartTimeBetweenAndClassTypeOrderByStartTimeAsc(startOfDay,endOfDay,classType)  // Fetch all classes
                .stream()
                .map(ClassDTO::new)  // Convert each Class to ClassDTO
                .collect(Collectors.toList()); // Collect as a List
    }

    // Get class by ID
    public ClassDTO getClassById(Integer classId) {
        if (classId == null || classId <= 0) {
            throw new IllegalArgumentException("Invalid class ID");
        }

        return classRepository.findById(classId)
                .map(ClassDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Class with ID " + classId + " not found"));
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

        ClassType classType = classTypeRepository.findById(classDTO.getTypeId())
                .orElseThrow(() -> new RuntimeException("Type not found for ID: " + classDTO.getTypeId()));

        // Map DTO properties to class entity
        currentClass.setClassName(classDTO.getClassName());
        currentClass.setStartTime(classDTO.getStartTime());
        currentClass.setEndTime(classDTO.getEndTime());
        currentClass.setMaxCapacity(classDTO.getMaxCapacity());
        currentClass.setCurrentBookingCount(0);
        currentClass.setLocation(classDTO.getLocation());
        currentClass.setAddressOne(classDTO.getAddressOne());
        currentClass.setAddressTwo(classDTO.getAddressTwo());
        currentClass.setAddressThree(classDTO.getAddressThree());
        currentClass.setCity(classDTO.getCity());
        currentClass.setBranch(branch); // Set the fetched branch entity
        currentClass.setStatus(status); // Set the fetched status entity
        currentClass.setInstructor(instructor); // Set the fetched instructor entity
        currentClass.setDescription(classDTO.getDescription());
        currentClass.setMinAge(classDTO.getMinAge());
        currentClass.setMaxAge(classDTO.getMaxAge());
        currentClass.setClassType(classType);
        currentClass.setAddWho(classDTO.getAddWho());
        currentClass.setAddDate(currentDate);
        currentClass.setEditWho(classDTO.getAddWho());
        currentClass.setEditDate(currentDate);

        // Save and return the class
        return classRepository.save(currentClass);
    }

    // Update an existing class
    public ClassDTO updateClass(Integer classId, ClassDTO classDTO) {
        if (classId == null || classId <= 0) {
            throw new IllegalArgumentException("Invalid class ID");
        }

        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class with ID " + classId + " not found"));

        LocalDateTime currentDate = LocalDateTime.now();

        // Update fields
        classEntity.setClassName(classDTO.getClassName());
        classEntity.setStartTime(classDTO.getStartTime());
        classEntity.setEndTime(classDTO.getEndTime());
        classEntity.setMaxCapacity(classDTO.getMaxCapacity());
        classEntity.setLocation(classDTO.getLocation());
        classEntity.setAddressOne(classDTO.getAddressOne());
        classEntity.setAddressTwo(classDTO.getAddressTwo());
        classEntity.setAddressThree(classDTO.getAddressThree());
        classEntity.setCity(classDTO.getCity());
        classEntity.setDescription(classDTO.getDescription());
        classEntity.setMinAge(classDTO.getMinAge());
        classEntity.setMaxAge(classDTO.getMaxAge());
        classEntity.setEditWho(classDTO.getEditWho());
        classEntity.setEditDate(currentDate);

        // Update related entities (instructor, branch, status)
        if (classDTO.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(classDTO.getInstructorId())
                    .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));
            classEntity.setInstructor(instructor);
        }

        if (classDTO.getBranchId() != null) {
            CodeLkup branch = codeLkupRepository.findById(classDTO.getBranchId())
                    .orElseThrow(() -> new EntityNotFoundException("Branch not found"));
            classEntity.setBranch(branch);
        }

        if (classDTO.getStatusId() != null) {
            CodeLkup status = codeLkupRepository.findById(classDTO.getStatusId())
                    .orElseThrow(() -> new EntityNotFoundException("Status not found"));
            classEntity.setStatus(status);
        }
        if (classDTO.getTypeId() != null) {
            ClassType classType = classTypeRepository.findById(classDTO.getTypeId())
                    .orElseThrow(() -> new RuntimeException("Type not found for ID: " + classDTO.getTypeId()));
            classEntity.setClassType(classType);
        }

        Class updatedEntity = classRepository.save(classEntity);
        return new ClassDTO(updatedEntity);
    }

    // Delete a class by ID
    public void deleteClass(Integer classId) {
        if (classId == null || classId <= 0) {
            throw new IllegalArgumentException("Invalid class ID");
        }

        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class with ID " + classId + " not found"));

        classRepository.delete(classEntity);
    }

    // get all classes by instructor
    public List<ClassDTO> getClassesByInstructorId(Integer instructorId) {
        if (instructorId == null || instructorId <= 0) {
            throw new IllegalArgumentException("Invalid instructor ID");
        }

        List<Class> classes = classRepository.findByInstructor_InstructorId(instructorId);

        if (classes.isEmpty()) {
            throw new EntityNotFoundException("No classes found for instructor ID " + instructorId);
        }

        return classes.stream().map(ClassDTO::new).collect(Collectors.toList());
    }

    // get all classes by type
    public List<ClassDTO> getClassesByType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Class type cannot be null or empty");
        }

        List<Class> classes = classRepository.findByClassType_Type(type);

        if (classes.isEmpty()) {
            throw new EntityNotFoundException("No classes found for type: " + type);
        }

        return classes.stream().map(ClassDTO::new).collect(Collectors.toList());
    }


}
