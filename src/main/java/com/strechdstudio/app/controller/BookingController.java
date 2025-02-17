package com.strechdstudio.app.controller;

import com.strechdstudio.app.model.Booking;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.service.BookingService;
import com.strechdstudio.app.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Get all classes
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    // Get class by ID
    @GetMapping("/{bookingId}")
    public Optional<Booking> getBookingById(@PathVariable Integer bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    // Create a new class
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.saveBooking(booking);
    }
}
