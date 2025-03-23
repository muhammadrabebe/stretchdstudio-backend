package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClassTypeRepository extends JpaRepository<ClassType, UUID> {
    ClassType findByType(String type);
    @Query("SELECT COUNT(CT) FROM ClassType CT")
    Integer countAllClassTypes();
}
