package com.gagani.smart_booking_platform.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BookingResponseDTO {

    private Long id;

    private String resourceName;
    private String resourceType;

    private Instant startTime;
    private Instant endTime;

    private String status;
    private String notes;

    private String userEmail;
    private Instant bookingDate;

    private Instant createdAt;

}
