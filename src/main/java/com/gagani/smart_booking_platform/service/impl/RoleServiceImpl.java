package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.entity.Role;
import com.gagani.smart_booking_platform.exception.ResourceNotFoundException;
import com.gagani.smart_booking_platform.repository.RoleRepository;
import com.gagani.smart_booking_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role not found with name: %s", name)));
    }

}
