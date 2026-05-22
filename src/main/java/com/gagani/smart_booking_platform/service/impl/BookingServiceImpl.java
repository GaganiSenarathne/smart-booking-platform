package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.dto.request.BookingRequestDTO;
import com.gagani.smart_booking_platform.dto.response.BookingResponseDTO;
import com.gagani.smart_booking_platform.entity.Booking;
import com.gagani.smart_booking_platform.entity.Resource;
import com.gagani.smart_booking_platform.entity.UserInfo;
import com.gagani.smart_booking_platform.entity.enums.BookingStatus;
import com.gagani.smart_booking_platform.exception.BookingConflictException;
import com.gagani.smart_booking_platform.exception.InvalidBookingException;
import com.gagani.smart_booking_platform.exception.ResourceNotFoundException;
import com.gagani.smart_booking_platform.exception.UnauthorizedException;
import com.gagani.smart_booking_platform.repository.BookingRepository;
import com.gagani.smart_booking_platform.repository.ResourceRepository;
import com.gagani.smart_booking_platform.repository.UserInfoRepository;
import com.gagani.smart_booking_platform.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public BookingResponseDTO createBooking(BookingRequestDTO dto, String email) {
        UserInfo user = userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("User not found with email: %s", email)
                ));

        Resource resource = resourceRepository.findById(dto.getResourceId())
                .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("Resource not found with id: %s", dto.getResourceId())
                ));

//        Time logic
        if(dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new InvalidBookingException(
                    "Invalid booking time range"
            );
        }

//        Conflict Check
        List<Booking> conflicts = bookingRepository.findConflictingBookings(dto.getResourceId(), dto.getStartTime(), dto.getEndTime());

        if(!conflicts.isEmpty()) {

            throw new BookingConflictException("Conflicting bookings found!");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setResource(resource);
        booking.setStatus(BookingStatus.CREATED);
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setNotes(dto.getNotes());

        Booking savedBooking = bookingRepository.save(booking);

        return mapToBookingDTO(savedBooking);
    }

    @Override
    public BookingResponseDTO cancelBooking(Long id, String email) {

        Booking booking = bookingRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(
                "Booking not found"
        ));

//        Checking Ownership
        if(!booking.getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("The user is not authorized to perform this action");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setModified(Instant.now());
        Booking savedCancelBooking = bookingRepository.save(booking);

        return mapToBookingDTO(savedCancelBooking);
    }

    @Override
    public Page<BookingResponseDTO> getMyBookings(String email, Pageable pageable) {

        Page<Booking> bookings = bookingRepository.findByUserEmail(email, pageable);
        return bookings.map(this::mapToBookingDTO);
    }

    @Override
    public Page<BookingResponseDTO> getAllBookings(Pageable pageable) {
        Page<Booking> allBookings = bookingRepository.findAll(pageable);
        return allBookings.map(this::mapToBookingDTO);
    }

    @Override
    public BookingResponseDTO confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(()-> new InvalidBookingException("Booking not found!"));
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setModified(Instant.now());
        Booking booking1 = bookingRepository.save(booking);
        return mapToBookingDTO(booking1);
    }

    @Override
    public BookingResponseDTO completeBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(()-> new InvalidBookingException("Booking not found!"));
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setModified(Instant.now());
        Booking booking1 = bookingRepository.save(booking);
        return mapToBookingDTO(booking1);
    }

    public BookingResponseDTO mapToBookingDTO(Booking booking) {

        BookingResponseDTO dto = new BookingResponseDTO();

        dto.setId(booking.getId());
        dto.setResourceName(booking.getResource().getName());
        dto.setResourceType(String.valueOf(booking.getResource().getType()));
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setStatus(booking.getStatus().name());
        dto.setNotes(booking.getNotes());
        dto.setCreatedAt(booking.getCreated());

        return dto;
    }
}
