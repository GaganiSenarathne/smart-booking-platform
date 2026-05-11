package com.gagani.smart_booking_platform.entity;

import com.gagani.smart_booking_platform.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Resource resource;

    private Instant startTime;
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String notes;

    private Instant created;
    private Instant modified;

    private Instant bookingDate;

}
