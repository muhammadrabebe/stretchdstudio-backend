package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.ClassPackageDTO;
import com.strechdstudio.app.model.ClassPackage;
import com.strechdstudio.app.model.ClassType;
import com.strechdstudio.app.repository.ClassPackageRepository;
import com.strechdstudio.app.repository.ClassTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClassPackageService {
    @Autowired
    private ClassPackageRepository classPackageRepository;

    @Autowired
    private ClassTypeRepository classTypeRepository;

    public List<ClassPackageDTO> getAllClassPackages() {
        return classPackageRepository.findAll()
                .stream()
                .map(classPackage -> {
                    ClassPackageDTO dto = new ClassPackageDTO();
                    dto.setClassPackageId(classPackage.getclassPackageId());
                    dto.setName(classPackage.getName());
                    dto.setDescription(classPackage.getDescription());
                    dto.setPrice(classPackage.getPrice());
                    dto.setTotalClasses(classPackage.getTotalClasses());
                    dto.setStartDate(classPackage.getStartDate());
                    dto.setExpiryDate(classPackage.getExpiryDate());
                    dto.setClassTypeId(classPackage.getClassType().getTypeId());
                    dto.setClassTypeName(classPackage.getClassType().getType());
                    // Set other DTO properties as needed
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public ClassPackageDTO getClassPackageById(UUID id) {
        return classPackageRepository.findById(id)
                .map(classPackage -> new ClassPackageDTO(
                        classPackage.getclassPackageId(),
                        classPackage.getName(),
                        classPackage.getDescription(),
                        classPackage.getPrice(),
                        classPackage.getTotalClasses(),
                        classPackage.getStartDate(),
                        classPackage.getExpiryDate(),
                        classPackage.getClassType().getTypeId(),
                        classPackage.getClassType().getType()
                ))
                .orElseThrow(() -> new NoSuchElementException("Class Package not found"));
    }


    public ClassPackage createClassPackage(ClassPackageDTO classPackageDTO) {
        // Check if the class package already exists
        ClassPackage existingClassPackage = classPackageRepository.findByName(classPackageDTO.getName());
        if (existingClassPackage != null) {
            throw new RuntimeException("Class Package " + classPackageDTO.getName() + " already exists.");
        }

        // Create a new class package instance
        ClassPackage currentClassPackage = new ClassPackage();
        LocalDateTime currentDate = LocalDateTime.now();

        // Fetch the related entities based on the provided IDs
        ClassType classType = classTypeRepository.findById(classPackageDTO.getClassTypeId())
                .orElseThrow(() -> new RuntimeException("Class type not found for ID: " + classPackageDTO.getClassTypeId()));

        // Map DTO properties to class package entity
        currentClassPackage.setName(classPackageDTO.getName());
        currentClassPackage.setDescription(classPackageDTO.getDescription());
        currentClassPackage.setPrice(classPackageDTO.getPrice());
        currentClassPackage.setTotalClasses(classPackageDTO.getTotalClasses());
        currentClassPackage.setStartDate(classPackageDTO.getStartDate());
        currentClassPackage.setExpiryDate(classPackageDTO.getExpiryDate());
        currentClassPackage.setClassType(classType);  // Set the fetched class type entity

        // Save and return the class package
        return classPackageRepository.save(currentClassPackage);
    }


    public ClassPackage updateClassPackage(UUID id, ClassPackageDTO updatedClassPackage) {
        ClassPackage existingClassPackage = classPackageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Class Package not found"));

        ClassType classType = classTypeRepository.findById(updatedClassPackage.getClassTypeId())
                .orElseThrow(() -> new NoSuchElementException("Class type not found"));
        try{
            existingClassPackage.setName(updatedClassPackage.getName());
            existingClassPackage.setDescription(updatedClassPackage.getDescription());
            existingClassPackage.setPrice(updatedClassPackage.getPrice());
            existingClassPackage.setTotalClasses(updatedClassPackage.getTotalClasses());
            existingClassPackage.setStartDate(updatedClassPackage.getStartDate());
            existingClassPackage.setExpiryDate(updatedClassPackage.getExpiryDate());
            existingClassPackage.setClassType(classType);

            return classPackageRepository.save(existingClassPackage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClassPackage(UUID id) {
        if (!classPackageRepository.existsById(id)) {
            throw new NoSuchElementException("Class Package not found");
        }
        classPackageRepository.deleteById(id);
    }
}

