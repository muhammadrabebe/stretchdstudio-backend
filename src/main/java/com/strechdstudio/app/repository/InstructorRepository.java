package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Class, Long> {

}
