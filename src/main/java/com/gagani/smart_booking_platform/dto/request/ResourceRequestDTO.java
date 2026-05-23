package com.gagani.smart_booking_platform.dto.request;

import com.gagani.smart_booking_platform.entity.Organization;
import com.gagani.smart_booking_platform.entity.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceRequestDTO {

    private Long id;
    private String name;
    private ResourceType type;
    private String description;
    private Long organizationId;
    private boolean active;
}
