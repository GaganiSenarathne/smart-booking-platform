package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.request.OrganizationRequestDTO;
import com.gagani.smart_booking_platform.dto.response.OrganizationRespondDTO;

public interface OrganizationService {

    OrganizationRespondDTO createOrganization(OrganizationRequestDTO organizationRequestDTO, String email);
}
