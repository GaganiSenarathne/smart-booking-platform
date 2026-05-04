package com.gagani.smart_booking_platform.controller;

import com.gagani.smart_booking_platform.dto.BookingDTO;
import com.gagani.smart_booking_platform.entity.Booking;
import com.gagani.smart_booking_platform.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO, email));

    }

    @GetMapping("/byEmail")
    public List<Booking> getBookings() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingService.getMyBookings(email);
    }

    @PutMapping("/{id}/cancel")
    public Booking cancelBooking(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingService.cancelBooking(id, email);
    }

}
