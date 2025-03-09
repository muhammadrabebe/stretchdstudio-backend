package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    List<Class> findByInstructor_InstructorId(Integer insturctorId);

    Class findByClassName(String className);

    List<Class> findByClassType_Type(String type);
}
