package com.gagani.smart_booking_platform.dto.request;

import com.gagani.smart_booking_platform.entity.Organization;
import com.gagani.smart_booking_platform.entity.Role;
import com.gagani.smart_booking_platform.entity.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private Set<Role> role;
    private String phone;
    private String address;
    private UserStatus status;
    private Organization organization;
//    private Long organizationId;
}
