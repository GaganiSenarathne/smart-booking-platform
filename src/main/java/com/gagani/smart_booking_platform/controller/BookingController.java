package com.gagani.smart_booking_platform.controller;

import com.gagani.smart_booking_platform.dto.BookingRequestDTO;
import com.gagani.smart_booking_platform.dto.BookingResponseDTO;
import com.gagani.smart_booking_platform.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(bookingService.createBooking(bookingRequestDTO, email));
    }

    @GetMapping("/byEmail")
    public Page<BookingResponseDTO> getBookings(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingService.getMyBookings(email, pageable);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<BookingResponseDTO> getAllBookings(Pageable pageable) {
        return bookingService.getAllBookings(pageable);
    }

    @PutMapping("/{id}/cancel")
    public BookingResponseDTO cancelBooking(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingService.cancelBooking(id, email);
    }

    @PutMapping("/confirm")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public BookingResponseDTO confirmBooking(@PathVariable Long id) {
        return bookingService.confirmBooking(id);
    }

}
