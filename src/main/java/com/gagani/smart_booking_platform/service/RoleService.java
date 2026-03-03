package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.entity.Role;

public interface RoleService {

    Role findByName(String name);
}
