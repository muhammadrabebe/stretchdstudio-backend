package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.BookingDTO;
import com.strechdstudio.app.dto.ClassDTO;
import com.strechdstudio.app.model.Booking;
import com.strechdstudio.app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    BookingService bookingService = new BookingService();

    // Constructor-based injection (Recommended)
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getAllBookingsWithDetails() {
        List<Booking> bookingList = bookingService.getAllBookingsWithDetails();
        ApiResponse<List<Booking>> response = new ApiResponse<>("success", 200, bookingList);
        return ResponseEntity.ok(response);
    }

    // Get all bookings
//    @GetMapping("/last-five-bookings")
//    public ResponseEntity<ApiResponse<List<BookingDTO>>> getLast5Bookings() {
//        List<BookingDTO> bookingList = bookingService.getLast5Bookings();
//        ApiResponse<List<BookingDTO>> response = new ApiResponse<>("success", 200, bookingList);
//        return ResponseEntity.ok(response);
//    }

    // Get all bookings
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getAllBookings() {
        List<BookingDTO> bookingList = bookingService.getAllBookings();
        ApiResponse<List<BookingDTO>> response = new ApiResponse<>("success", 200, bookingList);
        return ResponseEntity.ok(response);
    }


    // Get booking by ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Optional<Booking>>>getBookingById(@PathVariable Integer bookingId) {
        Optional<Booking> bookedClass = bookingService.getBookingById(bookingId);
        ApiResponse<Optional<Booking>> response = new ApiResponse<>("success", 200, bookedClass);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // create a booking
    @PostMapping
    public ResponseEntity<ApiResponse<BookingDTO>> createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        ApiResponse<BookingDTO> response = new ApiResponse<>("Booking has been completed.", 200, createdBooking);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // get all bookings by instructor
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingsByInstructorId(@PathVariable Integer instructorId) {
        List<BookingDTO> bookings = bookingService.getBookingsByInstructorId(instructorId);
        ApiResponse<List<BookingDTO>> response = new ApiResponse<>("success", 200, bookings);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingsByCustomerId(@PathVariable Integer customerId) {
        List<BookingDTO> bookings = bookingService.getBookingsByCustomerId(customerId);
        ApiResponse<List<BookingDTO>> response = new ApiResponse<>("success", 200, bookings);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingDTO>> updateBooking(@PathVariable Integer bookingId, @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(bookingId, bookingDTO);
        ApiResponse<BookingDTO> response = new ApiResponse<>("Booking has been updated.", 200, updatedBooking);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<String>> deleteBooking(@PathVariable Integer bookingId) {
        bookingService.deleteBooking(bookingId);
        ApiResponse<String> response = new ApiResponse<>("success", 200, "Booking deleted successfully");
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/active/count")
//    public ResponseEntity<ApiResponse<Integer>> getActiveInstructorCount() {
//        Integer count = bookingService.getActiveInstructorCount();
//        ApiResponse<Integer> response = new ApiResponse<>("success", 200, count);
//        return ResponseEntity.ok(response);
//    }

}
