package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.request.OrganizationRequestDTO;
import com.gagani.smart_booking_platform.dto.response.OrganizationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationService {

    OrganizationResponseDTO createOrganization(OrganizationRequestDTO organizationRequestDTO, String email);
    Page<OrganizationResponseDTO> getOrganizations(Pageable pageable);
    OrganizationResponseDTO getOrganizationById(Long id);
    OrganizationResponseDTO updateOrganization(OrganizationRequestDTO organizationRequestDTO, Long id);
    void deleteOrganization(Long id);
}
