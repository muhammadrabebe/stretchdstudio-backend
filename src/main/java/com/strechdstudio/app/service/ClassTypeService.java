package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.ClassTypeDTO;
import com.strechdstudio.app.model.ClassType;
import com.strechdstudio.app.repository.ClassTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ClassTypeService {

    private final ClassTypeRepository repository;

    public ClassTypeService(ClassTypeRepository repository) {
        this.repository = repository;
    }

    public List<ClassType> getAllClassTypes() {
        return repository.findAll();
    }

    // Get class by ID
    public ClassTypeDTO getClassTypeById(UUID classTypeId) {
        if (classTypeId == null) {
            throw new IllegalArgumentException("Invalid class ID");
        }

        return repository.findById(classTypeId)
                .map(ClassTypeDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("ClassType with ID " + classTypeId + " not found"));
    }

    public ClassType createClassType(ClassType classType) {
        // Check if a ClassType with the same type already exists
        ClassType existingClassType = repository.findByType(classType.getType());
        if (existingClassType == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class type already exists");


        return repository.save(classType);
    }

    public ClassType updateClassType(UUID typeId, ClassType updatedClassType) {
        // Check if ClassType exists
        ClassType existingClassType = repository.findById(typeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class type not found"));

        // Ensure unique type name
//        if (!existingClassType.getType().equals(updatedClassType.getType()) &&
//                repository.findByType(updatedClassType.getType()).isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class type already exists");
//        }

        // Update fields
        existingClassType.setType(updatedClassType.getType());
        existingClassType.setImageUrl(updatedClassType.getImageUrl());

        return repository.save(existingClassType);
    }

    public void deleteClassType(UUID typeId) {
        // Check if ClassType exists
        if (!repository.existsById(typeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class type not found");
        }
        repository.deleteById(typeId);
    }

    public Integer getActiveClassTypeCount() {
        return repository.countAllClassTypes();
    }
}
