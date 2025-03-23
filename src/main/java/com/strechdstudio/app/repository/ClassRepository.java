package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    List<Class> findByInstructor_InstructorId(Integer insturctorId);

    Class findByClassName(String className);

    List<Class> findByClassType_Type(String type);

    List<Class> findByStartTimeBetweenOrderByStartTimeAsc(LocalDateTime start, LocalDateTime end);

    List<Class> findByStartTimeBetweenAndClassTypeOrderByStartTimeAsc(LocalDateTime start, LocalDateTime end, ClassType classType);

}
