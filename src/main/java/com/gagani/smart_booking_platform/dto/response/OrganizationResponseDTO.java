package com.gagani.smart_booking_platform.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrganizationResponseDTO {

    private String name;
    private String address;
    private String email;
    private String phone;
    private Instant created_at;
    private Instant updated_at;
}
