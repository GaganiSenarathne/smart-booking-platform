package com.gagani.smart_booking_platform.repository;

import com.gagani.smart_booking_platform.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Resource findByName(String name);

    @Query("""
    SELECT r
    FROM Resource r
    WHERE r.id NOT IN (

        SELECT b.resource.id
        FROM Booking b
        WHERE b.status <> 'CANCELLED'
        AND (
            :start < b.endTime
            AND :end > b.startTime
        )
    )
""")
    List<Resource> findAvailableResources(
            @Param("start") Instant start,
            @Param("end") Instant end
    );
}
