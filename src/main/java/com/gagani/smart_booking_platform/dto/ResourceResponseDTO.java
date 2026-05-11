package com.gagani.smart_booking_platform.dto;

import com.gagani.smart_booking_platform.entity.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceResponseDTO {

    private Long id;
    private String name;
    private ResourceType type;
    private boolean active;
    private String description;
}
