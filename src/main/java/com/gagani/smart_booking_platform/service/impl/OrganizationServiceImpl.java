package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.dto.request.BookingRequestDTO;
import com.gagani.smart_booking_platform.dto.request.OrganizationRequestDTO;
import com.gagani.smart_booking_platform.dto.response.OrganizationRespondDTO;
import com.gagani.smart_booking_platform.entity.Organization;
import com.gagani.smart_booking_platform.exception.DuplicateResourceException;
import com.gagani.smart_booking_platform.repository.OrganizationRepository;
import com.gagani.smart_booking_platform.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    public final OrganizationRepository organizationRepository;

    @Override
    public OrganizationRespondDTO createOrganization(OrganizationRequestDTO organizationRequestDTO, String email) {

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

        return mapToOrgRespondDTO(savedOrganization);

    }


    public OrganizationRespondDTO mapToOrgRespondDTO(Organization organization) {

        OrganizationRespondDTO organizationRespondDTO = new OrganizationRespondDTO();
        organizationRespondDTO.setName(organization.getName());
        organizationRespondDTO.setEmail(organization.getEmail());
        organizationRespondDTO.setPhone(organization.getPhone());
        organizationRespondDTO.setAddress(organization.getAddress());
        organizationRespondDTO.setCreated_at(organization.getCreated_at());
        organizationRespondDTO.setUpdated_at(organization.getUpdated_at());

        return organizationRespondDTO;
    }
}
