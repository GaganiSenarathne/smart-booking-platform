package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.dto.request.OrganizationRequestDTO;
import com.gagani.smart_booking_platform.dto.response.OrganizationResponseDTO;
import com.gagani.smart_booking_platform.entity.Organization;
import com.gagani.smart_booking_platform.exception.DuplicateResourceException;
import com.gagani.smart_booking_platform.exception.ResourceNotFoundException;
import com.gagani.smart_booking_platform.repository.OrganizationRepository;
import com.gagani.smart_booking_platform.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    public final OrganizationRepository organizationRepository;

    @Override
    public OrganizationResponseDTO createOrganization(OrganizationRequestDTO organizationRequestDTO, String email) {

        Organization existingOrg = organizationRepository.findByName(organizationRequestDTO.getName());
        if (existingOrg != null) {

            throw new DuplicateResourceException("Organization already exists!");
        }

        Organization organization = new Organization();

        organization.setName(organizationRequestDTO.getName());
        organization.setEmail(email);
        organization.setCreated_at(Instant.now());
        organization.setUpdated_at(Instant.now());
        organization.setPhone(organizationRequestDTO.getPhone());
        organization.setAddress(organizationRequestDTO.getAddress());
        Organization savedOrganization = organizationRepository.save(organization);

        return mapToOrgResponseDTO(savedOrganization);

    }

    @Override
    public Page<OrganizationResponseDTO> getOrganizations(Pageable pageable) {
        Page<Organization> organization = organizationRepository.findAll(pageable);
        return organization.map(this::mapToOrgResponseDTO);
    }

    @Override
    public OrganizationResponseDTO getOrganizationById(Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organization not found!"));
        return mapToOrgResponseDTO(organization);
    }

    @Override
    public OrganizationResponseDTO updateOrganization(OrganizationRequestDTO organizationRequestDTO, Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organization not found!"));

        organization.setName(organizationRequestDTO.getName());
        organization.setEmail(organizationRequestDTO.getEmail());
        organization.setUpdated_at(Instant.now());
        organization.setPhone(organizationRequestDTO.getPhone());
        organization.setAddress(organizationRequestDTO.getAddress());
        Organization savedOrganization = organizationRepository.save(organization);

        return mapToOrgResponseDTO(savedOrganization);
    }

    public void deleteOrganization(Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        organizationRepository.delete(organization);
    }

    public OrganizationResponseDTO mapToOrgResponseDTO(Organization organization) {

        OrganizationResponseDTO organizationResponseDTO = new OrganizationResponseDTO();

        organizationResponseDTO.setName(organization.getName());
        organizationResponseDTO.setEmail(organization.getEmail());
        organizationResponseDTO.setPhone(organization.getPhone());
        organizationResponseDTO.setAddress(organization.getAddress());
        organizationResponseDTO.setCreated_at(organization.getCreated_at());
        organizationResponseDTO.setUpdated_at(organization.getUpdated_at());

        return organizationResponseDTO;
    }
}
