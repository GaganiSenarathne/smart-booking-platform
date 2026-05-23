package com.gagani.smart_booking_platform.config;

import com.gagani.smart_booking_platform.entity.Organization;
import com.gagani.smart_booking_platform.entity.Resource;
import com.gagani.smart_booking_platform.entity.Role;
import com.gagani.smart_booking_platform.entity.UserInfo;
import com.gagani.smart_booking_platform.entity.enums.ResourceType;
import com.gagani.smart_booking_platform.entity.enums.UserStatus;
import com.gagani.smart_booking_platform.repository.OrganizationRepository;
import com.gagani.smart_booking_platform.repository.ResourceRepository;
import com.gagani.smart_booking_platform.repository.RoleRepository;
import com.gagani.smart_booking_platform.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResourceRepository resourceRepository;

    @PostConstruct
    public void initData() {

        // ================= ROLES =================
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_STAFF");
        createRoleIfNotExists("ROLE_CUSTOMER");

        // ================= ORGANIZATION =================
        Organization org;

        if (organizationRepository.count() == 0) {

            org = new Organization();
            org.setName("DEFAULT_ORG");
            org.setEmail("system@org.com");
            org.setAddress("SYSTEM");
            org.setCreated_at(Instant.now());

            org = organizationRepository.save(org);

        } else {
            org = organizationRepository.findAll().getFirst();
        }

        // ================= ADMIN USER =================
        if (userInfoRepository.count() == 0) {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow();

            UserInfo admin = new UserInfo();
            admin.setName("System Admin");
            admin.setEmail("admin@system.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setOrganization(org);
            admin.setStatus(UserStatus.ACTIVE);
            admin.setCreated_at(Instant.now());
            admin.setRoles(Set.of(adminRole));

            userInfoRepository.save(admin);
        }

        // ================= DEFAULT RESOURCE =================
        if (resourceRepository.count() == 0) {

            Resource r = new Resource();
            r.setName("Default Room");
            r.setDescription("System generated resource");
            r.setType(ResourceType.ROOM);
            r.setActive(true);
            r.setOrganization(org);

            resourceRepository.save(r);
        }
    }

    private void createRoleIfNotExists(String roleName) {

        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
        }
    }
}