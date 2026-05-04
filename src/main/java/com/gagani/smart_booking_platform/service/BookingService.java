package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.BookingDTO;
import com.gagani.smart_booking_platform.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(BookingDTO dto, String email);
    Booking cancelBooking(Long id, String email);
    List<Booking> getMyBookings(String email);

}
