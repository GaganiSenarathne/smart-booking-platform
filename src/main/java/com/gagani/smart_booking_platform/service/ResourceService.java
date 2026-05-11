package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.ResourceRequestDTO;
import com.gagani.smart_booking_platform.dto.ResourceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceService {

    ResourceResponseDTO createResource(ResourceRequestDTO resourceRequestDTO, String email);
    ResourceResponseDTO updateResource(Long id,ResourceRequestDTO resourceRequestDTO);
    Page<ResourceResponseDTO> getAllResources(Pageable pageable);
    ResourceResponseDTO getResourceById(Long id);
    void deleteResource(Long id);

}
