package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.BookingDTO;
import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.ClassPackageDTO;
import com.strechdstudio.app.model.Booking;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.Instructor;
import com.strechdstudio.app.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    public BookingRepository bookingRepository;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public InstructorRepository instructorRepository;
    @Autowired
    public CodeLkupRepository codeLkupRepository;
    @Autowired
    public ClassRepository classRepository;

    // Get all bookings with details
    public List<Booking> getAllBookingsWithDetails() {
        List<Booking> Bookings = bookingRepository.findAll();
        return Bookings;
    }

    // get all bookings
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    // get all upcoming bookings
    public List<ClassDTO> getUpcomingBookings(Integer customerId) {
        return bookingRepository.findUpcomingBookedClassesByCustomer(customerId,LocalDateTime.now())
                .stream()
                .map(ClassDTO::new)
                .collect(Collectors.toList());
    }

    // get all past bookings
    public List<ClassDTO> getPastBookings(Integer customerId) {
        return bookingRepository.findPastBookedClassesByCustomer(customerId,LocalDateTime.now())
                .stream()
                .map(ClassDTO::new)
                .collect(Collectors.toList());
    }

    // get all upcoming bookings
    public List<ClassDTO> getNextUpcomingBookedClass(Integer customerId) {
        return bookingRepository.findTopBookedClassesByCustomer(customerId,LocalDateTime.now())
                .stream()
                .map(ClassDTO::new)
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .map(BookingDTO::new)
                .orElseThrow(() -> new NoSuchElementException("Booking not found"));
    }



    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Convert DTO to entity
        LocalDateTime currentDate = LocalDateTime.now();

        // Retrieve related entities
        Customer customer = customerRepository.findById(bookingDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Class bookedClass = classRepository.findById(bookingDTO.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        CodeLkup status = codeLkupRepository.findByCodelist_ListNameAndCode("BOOKINGSTATUS","Booked")
                .orElseThrow(() -> new EntityNotFoundException("Status not found"));
        Instructor instructor = instructorRepository.findById(bookedClass.getInstructor().getInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

//         Check if the customer has already booked the same class with the same instructor
        boolean bookingExists = bookingRepository.existsByCustomerAndBookedClass(customer, bookedClass);
        if (bookingExists) {
            throw new IllegalArgumentException("You already booked this class");
        }

        // Create and save the booking
        Booking currentBooking = new Booking();
        currentBooking.setCustomer(customer);
        currentBooking.setBookedClass(bookedClass);
        currentBooking.setStatus(status);
        currentBooking.setInstructor(instructor);
        currentBooking.setAddWho(bookingDTO.getAddWho());
        currentBooking.setAddDate(currentDate);
        currentBooking.setEditWho(bookingDTO.getAddWho());
        currentBooking.setEditDate(currentDate);

        Booking savedBooking = bookingRepository.save(currentBooking);

        // Return DTO
        return new BookingDTO(savedBooking);
    }


    public BookingDTO updateBooking(Integer bookingId, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        // Update fields
        existingBooking.setCustomer(customerRepository.findById(bookingDTO.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found")));
        existingBooking.setBookedClass(classRepository.findById(bookingDTO.getClassId()).orElseThrow(() -> new EntityNotFoundException("Class not found")));
        existingBooking.setStatus(codeLkupRepository.findById(bookingDTO.getStatusId()).orElseThrow(() -> new EntityNotFoundException("Status not found")));
        existingBooking.setInstructor(instructorRepository.findById(bookingDTO.getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor not found")));

        // Save the updated booking
        Booking updatedBooking = bookingRepository.save(existingBooking);

        // Return updated DTO
        return new BookingDTO(updatedBooking);
    }

    public void deleteBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        // Delete the booking
        bookingRepository.delete(booking);
    }

    public List<BookingDTO> getBookingsByInstructorId(Integer instructorId) {
        if (instructorId == null || instructorId <= 0) {
            throw new IllegalArgumentException("Invalid instructor ID");
        }

        List<Booking> bookings = bookingRepository.findByInstructor_InstructorId(instructorId);

        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("No bookings found for instructor ID " + instructorId);
        }

        return bookings.stream().map(BookingDTO::new).collect(Collectors.toList());
    }

    public List<BookingDTO> getBookingsByCustomerId(Integer customerId) {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        List<Booking> bookings = bookingRepository.findByCustomer_CustomerId(customerId);

        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("No bookings found for customer ID " + customerId);
        }

        return bookings.stream().map(BookingDTO::new).collect(Collectors.toList());
    }

//    public List<BookingDTO> getLast5Bookings() {
//        return bookingRepository.findTop5ByStatus_StatusNameNotAndBookedClass_StartTimeAfterOrderByBookedClass_StartTimeAsc(
//                        "Cancelled", LocalDateTime.now()) // Fetch all bookings
//                .stream()
//                .map(BookingDTO::new)  // Convert each Booking to BookingDTO
//                .collect(Collectors.toList()); // Collect as a List
//    }
}
