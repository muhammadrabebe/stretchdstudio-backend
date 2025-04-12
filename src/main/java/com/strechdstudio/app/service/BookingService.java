package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.BookingDTO;
import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.dto.ClassPackageDTO;
import com.strechdstudio.app.model.*;
import com.strechdstudio.app.model.Class;
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
    public CustomerPackageRepository customerPackageRepository;
    @Autowired
    public InstructorRepository instructorRepository;
    @Autowired
    public CodeLkupRepository codeLkupRepository;
    @Autowired
    public ClassRepository classRepository;
    @Autowired
    public ClassTypeRepository classTypeRepository;

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
        return bookingRepository.findUpcomingBookedClassesByCustomer(customerId, LocalDateTime.now())
                .stream()
                .map(ClassDTO::new)
                .collect(Collectors.toList());
    }

    // get all past bookings
    public List<ClassDTO> getPastBookings(Integer customerId) {
        return bookingRepository.findPastBookedClassesByCustomer(customerId, LocalDateTime.now())
                .stream()
                .map(ClassDTO::new)
                .collect(Collectors.toList());
    }

    // get all upcoming bookings
    public List<ClassDTO> getNextUpcomingBookedClass(Integer customerId) {
        return bookingRepository.findTopBookedClassesByCustomer(customerId, LocalDateTime.now())
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
        CodeLkup status = codeLkupRepository.findByCodelist_ListNameAndCode("BOOKINGSTATUS", "Booked")
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
        currentBooking.setAddWho(customer.getFullname());
        currentBooking.setAddDate(currentDate);
        currentBooking.setEditWho(customer.getFullname());
        currentBooking.setEditDate(currentDate);

        Booking savedBooking = bookingRepository.save(currentBooking);

        // update the count of the booked class
        updateBookedClass(customer, bookedClass);

        // Return DTO
        return new BookingDTO(savedBooking);
    }


    public BookingDTO updateBooking(Integer bookingId, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        CodeLkup attendedStatus = codeLkupRepository.findByCodelist_ListNameAndCode("BOOKINGSTATUS", "Attended")
                .orElseThrow(() -> new EntityNotFoundException("Attended status not found"));

        Class bookedClass = classRepository.findById(bookingDTO.getClassId()).orElseThrow(() -> new EntityNotFoundException("Class not found"));
        Customer customer = customerRepository.findById(bookingDTO.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Update fields
        existingBooking.setCustomer(customer);
        existingBooking.setBookedClass(bookedClass);
        existingBooking.setInstructor(instructorRepository.findById(bookingDTO.getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor not found")));

        CodeLkup updatedStatus = codeLkupRepository.findById(bookingDTO.getStatusId()).orElseThrow(() -> new EntityNotFoundException("Status not found"));
        // check if it is updated to attended in order to count the class
        if (updatedStatus.getCode().equals(attendedStatus.getCode())) {
            // update customer field
            customer.setTotalClassesAttended(customer.getTotalClassesAttended() + 1);
            customerRepository.save(customer);

            // update customer package

//            Optional<CustomerPackage> optionalPackage = customerPackageRepository.findByCustomerAndClasstype(customer, bookedClass.getClassType());
//
//            optionalPackage.ifPresent(customerPackage -> {
//                int remaining = customerPackage.getRemainingClasses();
//                String bookedType = bookedClass.getClassType().getType();
//                String packageType = customerPackage.getClassPackage().getClassType().getType();
//
//                if (remaining > 1 && packageType.equals(bookedType)) {
//                    customerPackage.setRemainingClasses(remaining - 1);
//                }
//            });


        }


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

    public void updateBookedClass(Customer customer, Class bookedClass) {
        bookedClass.setCurrentBookingCount(bookedClass.getCurrentBookingCount() + 1);
        if (bookedClass.getMaxCapacity().equals(bookedClass.getCurrentBookingCount())) {
            // check if the class reached the max so we update the status to waitlist.
            CodeLkup waitlistStatus = codeLkupRepository.findByCodelist_ListNameAndCode("CLASSSTATUS", "Waitlist")
                    .orElseThrow(() -> new RuntimeException("Status not found"));
            bookedClass.setStatus(waitlistStatus);
        }
        // get the class type of the booked class
        ClassType classType = bookedClass.getClassType();

        CodeLkup validStatus = codeLkupRepository.findByCodelist_ListNameAndCode("CUSTOMERPACKAGESTATUS", "Valid")
                .orElseThrow(() -> new RuntimeException("Valid status not found"));

        // 2. Get active customer package for that class type
        CustomerPackage customerPackage = customerPackageRepository
                .findByCustomerAndClassTypeAndStatusId(customer.getCustomerId(), classType.getTypeId(), validStatus.getCodeLkupId());

        if (customerPackage != null){
            customerPackage.setRemainingClasses(customerPackage.getRemainingClasses() - 1);

            // 5. Optionally update status to Completed
            if (customerPackage.getRemainingClasses() == 0) {
                CodeLkup completedStatus = codeLkupRepository
                        .findByCodelist_ListNameAndCode("CUSTOMERPACKAGESTATUS", "Completed")
                        .orElseThrow(null);
                customerPackage.setStatus(completedStatus);
            }

            customerPackageRepository.save(customerPackage);
        }

        classRepository.save(bookedClass);
    }

    public Booking updateBookingStatus(int bookingId, String status) {
        // Fetch the new status CodeLkup
        CodeLkup bookingStatus = codeLkupRepository.findByCodelist_ListNameAndCode("BOOKINGSTATUS", status)
                .orElseThrow(() -> new EntityNotFoundException(status + " status not found"));

        // Get current order
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found."));

        String currentStatus = booking.getStatus().getCode();

//        // Define allowed transitions
//        if (currentStatus.equalsIgnoreCase("pending")) {
//            if (!newStatus.equalsIgnoreCase("paid")) {
//                throw new RuntimeException("Invalid status transition: Pending can only be updated to Paid.");
//            }
//        } else if (currentStatus.equalsIgnoreCase("paid")) {
//            if(newStatus.equalsIgnoreCase("paid"))
//                throw new RuntimeException("The order is already paid.");
//            else if (!newStatus.equalsIgnoreCase("cancelled"))
//                throw new RuntimeException("The order is paid, you can't change it.");
//        } else {
//            throw new RuntimeException("Status update not allowed from status: " + currentStatus);
//        }

        // Update and save
        booking.setStatus(bookingStatus);
        booking.setEditDate(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

//    public List<BookingDTO> getLast5Bookings() {
//        return bookingRepository.findTop5ByStatus_StatusNameNotAndBookedClass_StartTimeAfterOrderByBookedClass_StartTimeAsc(
//                        "Cancelled", LocalDateTime.now()) // Fetch all bookings
//                .stream()
//                .map(BookingDTO::new)  // Convert each Booking to BookingDTO
//                .collect(Collectors.toList()); // Collect as a List
//    }
}
