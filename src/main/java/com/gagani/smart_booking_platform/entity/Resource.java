package com.gagani.smart_booking_platform.entity;

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

    private String type; // e.g. DOCTOR, ROOM, EQUIPMENT

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
