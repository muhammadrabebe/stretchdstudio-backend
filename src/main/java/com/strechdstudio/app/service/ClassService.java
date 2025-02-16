package com.strechdstudio.app.service;

import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    
    @Autowired
    public ClassRepository classRepository;

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
    public Class saveClass(Class classEntity) {
        return classRepository.save(classEntity);
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
