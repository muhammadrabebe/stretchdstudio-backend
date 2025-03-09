package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.Booking;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByInstructor_InstructorId(Integer instructorId);
    List<Booking> findByCustomer_CustomerId(Integer customerId);
    boolean existsByCustomerAndBookedClass(Customer customer, Class bookedClass);
//    List<Booking> findTop5ByStatus_StatusNameNotAndBookedClass_StartTimeAfterOrderByBookedClass_StartTimeAsc(String statusName, LocalDateTime now);
}
