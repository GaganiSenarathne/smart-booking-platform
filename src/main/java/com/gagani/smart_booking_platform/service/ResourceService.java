package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.request.ResourceRequestDTO;
import com.gagani.smart_booking_platform.dto.response.ResourceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface ResourceService {

    ResourceResponseDTO createResource(ResourceRequestDTO resourceRequestDTO, String email);
    ResourceResponseDTO updateResource(Long id,ResourceRequestDTO resourceRequestDTO);
    Page<ResourceResponseDTO> getAllResources(Pageable pageable);
    ResourceResponseDTO getResourceById(Long id);
    void deleteResource(Long id);
    List<ResourceResponseDTO> getAvailableResources(
            Instant start,
            Instant end
    );

}
