package com.gagani.smart_booking_platform.repository;

import com.gagani.smart_booking_platform.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
