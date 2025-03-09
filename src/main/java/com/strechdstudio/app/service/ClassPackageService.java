package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.ClassPackageDTO;
import com.strechdstudio.app.model.ClassPackage;
import com.strechdstudio.app.repository.ClassPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClassPackageService {
    @Autowired
    private ClassPackageRepository classPackageRepository;

    public List<ClassPackageDTO> getAllClassPackages() {
        return classPackageRepository.findAll()  // Fetch all classes
                .stream()
                .map(ClassPackageDTO::new)  // Convert each Class to ClassDTO
                .collect(Collectors.toList()); // Collect as a List
    }

    public ClassPackage getClassPackageById(UUID id) {
        return classPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class Package not found"));
    }

    public ClassPackage createClassPackage(ClassPackage classPackage) {
        return classPackageRepository.save(classPackage);
    }

    public ClassPackage updateClassPackage(UUID id, ClassPackage updatedClassPackage) {
        ClassPackage existingClassPackage = classPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class Package not found"));

        existingClassPackage.setName(updatedClassPackage.getName());
        existingClassPackage.setDescription(updatedClassPackage.getDescription());
        existingClassPackage.setPrice(updatedClassPackage.getPrice());
        existingClassPackage.setTotalClasses(updatedClassPackage.getTotalClasses());
        existingClassPackage.setStartDate(updatedClassPackage.getStartDate());
        existingClassPackage.setExpiryDate(updatedClassPackage.getExpiryDate());
        existingClassPackage.setClassType(updatedClassPackage.getClassType());

        return classPackageRepository.save(existingClassPackage);
    }

    public void deleteClassPackage(UUID id) {
        if (!classPackageRepository.existsById(id)) {
            throw new RuntimeException("Class Package not found");
        }
        classPackageRepository.deleteById(id);
    }
}

