package com.gagani.smart_booking_platform.entity;

import com.gagani.smart_booking_platform.entity.enums.ResourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType type; // e.g. DOCTOR, ROOM, EQUIPMENT

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo createdBy;

}
