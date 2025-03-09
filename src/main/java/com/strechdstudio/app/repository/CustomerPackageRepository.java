package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.CustomerPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerPackageRepository extends JpaRepository<CustomerPackage, UUID> {}
