package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.ClassPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassPackageRepository extends JpaRepository<ClassPackage, UUID> {}
