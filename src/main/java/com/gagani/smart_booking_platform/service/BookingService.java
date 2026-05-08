package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.BookingRequestDTO;
import com.gagani.smart_booking_platform.dto.BookingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO dto, String email);
    BookingResponseDTO cancelBooking(Long id, String email);
    Page<BookingResponseDTO> getMyBookings(String email, Pageable pageable);
    Page<BookingResponseDTO> getAllBookings(Pageable pageable);
    BookingResponseDTO confirmBooking(Long id);

}
