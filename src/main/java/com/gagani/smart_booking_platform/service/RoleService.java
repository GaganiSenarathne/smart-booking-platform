package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.response.RoleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    RoleResponseDTO findRoleByName(String name);
    Page<RoleResponseDTO> findAllRoles(Pageable pageable);
}
