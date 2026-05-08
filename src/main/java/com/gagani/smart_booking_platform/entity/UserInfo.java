package com.gagani.smart_booking_platform.entity;

import com.gagani.smart_booking_platform.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String address;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false, updatable = false)
    private Instant created_at;

    @Column(nullable = false)
    private Instant updated_at;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;


    @PrePersist
    protected void onCreate() {
        this.created_at = Instant.now();
        this.updated_at = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = Instant.now();
    }
}
