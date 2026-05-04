package com.gagani.smart_booking_platform.repository;

import com.gagani.smart_booking_platform.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserEmail(String email);

    @Query("""
        SELECT b FROM Booking b
        WHERE b.resource.id = :resourceId
        AND b.status IN ('CREATED', 'CONFIRMED')
        AND (
            (:start < b.endTime AND :end < b.startTime AND :end < b.endTime)
        )
    """)
    List<Booking> findConflictingBookings(@Param("resourceId") Long resourceId, @Param("start") Instant start, @Param("end") Instant end);
}
