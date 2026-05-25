package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.dto.response.RoleResponseDTO;
import com.gagani.smart_booking_platform.entity.Role;
import com.gagani.smart_booking_platform.exception.ResourceNotFoundException;
import com.gagani.smart_booking_platform.repository.RoleRepository;
import com.gagani.smart_booking_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleResponseDTO findRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role not found with name: %s", name)));

        return mapToRoleDTO(role);
    }

    @Override
    public Page<RoleResponseDTO> findAllRoles(Pageable pageable) {
        Page<Role> role = roleRepository.findAll(pageable);

        return role.map(this::mapToRoleDTO);
    }

    public RoleResponseDTO mapToRoleDTO(Role role) {

        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();

        roleResponseDTO.setId(role.getId());
        roleResponseDTO.setName(role.getName());
        roleResponseDTO.setUserInfo(role.getUsers());

        return roleResponseDTO;
    }

}
