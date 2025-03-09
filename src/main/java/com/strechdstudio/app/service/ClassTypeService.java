package com.strechdstudio.app.service;

import com.strechdstudio.app.model.ClassType;
import com.strechdstudio.app.repository.ClassTypeRepository;
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

    public ClassType createClassType(ClassType classType) {
        // Check if a ClassType with the same type already exists
        if (repository.findByType(classType.getType()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class type already exists");
        }

        return repository.save(classType);
    }

    public ClassType updateClassType(UUID typeId, ClassType updatedClassType) {
        // Check if ClassType exists
        ClassType existingClassType = repository.findById(typeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class type not found"));

        // Ensure unique type name
        if (!existingClassType.getType().equals(updatedClassType.getType()) &&
                repository.findByType(updatedClassType.getType()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class type already exists");
        }

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
