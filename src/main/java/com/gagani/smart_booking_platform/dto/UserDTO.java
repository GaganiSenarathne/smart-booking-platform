package com.gagani.smart_booking_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private Long organizationId;
}
