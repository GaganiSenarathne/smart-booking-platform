package com.gagani.smart_booking_platform.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrganizationRequestDTO {

    private String name;
    private String address;
    private String email;
    private String phone;
    private Instant created_at;
    private Instant updated_at;
}
