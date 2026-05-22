package com.gagani.smart_booking_platform.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BookingRequestDTO {

    private Long resourceId;
    private Instant startTime;
    private Instant endTime;
    private String notes;
    private Instant bookingDate;
}
