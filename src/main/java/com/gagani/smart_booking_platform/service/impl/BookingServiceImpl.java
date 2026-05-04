package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.dto.BookingDTO;
import com.gagani.smart_booking_platform.entity.Booking;
import com.gagani.smart_booking_platform.entity.Resource;
import com.gagani.smart_booking_platform.entity.UserInfo;
import com.gagani.smart_booking_platform.entity.enums.BookingStatus;
import com.gagani.smart_booking_platform.repository.BookingRepository;
import com.gagani.smart_booking_platform.repository.ResourceRepository;
import com.gagani.smart_booking_platform.repository.UserInfoRepository;
import com.gagani.smart_booking_platform.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ResourceRepository resourceRepository;
    private final UserInfoRepository userInfoRepository;


    @Override
    public Booking createBooking(BookingDTO dto, String email) {

        UserInfo user = userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not Found!"));

        Resource resource = resourceRepository.findById(dto.getResourceId())
                .orElseThrow(()-> new RuntimeException("Resource not Found!"));

//        Time logic
        if(dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new RuntimeException("Start Time must be after End Time!");
        }

//        Conflict Check
        List<Booking> conflicts = bookingRepository.findConflictingBookings(dto.getResourceId(), dto.getStartTime(), dto.getEndTime());

        if(!conflicts.isEmpty()) {

            throw new RuntimeException("Conflicting bookings found!");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setResource(resource);
        booking.setStatus(BookingStatus.CREATED);
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setNotes(dto.getNotes());

        return bookingRepository.save(booking);


    }

    @Override
    public Booking cancelBooking(Long id, String email) {

        Booking booking = bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Booking not found!"));

//        Checking Ownership
        if(!booking.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Not Allowed!");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setModified(Instant.now());
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getMyBookings(String email) {
        return bookingRepository.findByUserEmail(email);
    }
}
