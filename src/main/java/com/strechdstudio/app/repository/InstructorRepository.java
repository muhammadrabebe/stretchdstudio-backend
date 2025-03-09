package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Instructor findByPhoneNumber(String phoneNumber);
    @Query("SELECT COUNT(i) FROM Instructor i WHERE i.status.codelist.listName = 'INSTRUCTORSTATUS' AND i.status.code = 'Active'")
    Integer countActiveInstructors();
}
