package com.strechdstudio.app.service;

import com.strechdstudio.app.model.Booking;
import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    public BookingRepository bookingRepository;

    // Get all classes
    public List<Booking> getAllBookings() {
        List<Booking> Bookings = bookingRepository.findAll();
        return Bookings;
    }

    // Get class by ID
    public Optional<Booking> getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
    }

    // Save a new class
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Update an existing class
    public Booking updatedBooking(Integer bookingId, Booking updatedBooking) {
        if (bookingRepository.existsById(bookingId)) {
            return bookingRepository.save(updatedBooking);
        } else {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }
    }

    // Delete a class by ID
    public void deleteBooking(Integer bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
        } else {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }
    }
}
