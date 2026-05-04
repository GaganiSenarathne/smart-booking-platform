package com.gagani.smart_booking_platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BookingDTO {

    private Long resourceId;
    private Instant startTime;
    private Instant endTime;
    private String notes;
}
