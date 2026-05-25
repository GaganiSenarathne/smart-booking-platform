package com.gagani.smart_booking_platform.controller;

import com.gagani.smart_booking_platform.dto.request.OrganizationRequestDTO;
import com.gagani.smart_booking_platform.dto.response.OrganizationResponseDTO;
import com.gagani.smart_booking_platform.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<?> createOrganization(@RequestBody OrganizationRequestDTO organizationRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(organizationService.createOrganization(organizationRequestDTO, email));
    }

    @GetMapping
    public ResponseEntity<Page<OrganizationResponseDTO>> getOrganizations(Pageable pageable) {
        return ResponseEntity.ok(organizationService.getOrganizations(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponseDTO> getOrganizationById(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.getOrganizationById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<OrganizationResponseDTO> updateOrganization(@RequestBody OrganizationRequestDTO organizationRequestDTO, @PathVariable Long id) {
        return ResponseEntity.ok(organizationService.updateOrganization(organizationRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationResponseDTO> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}
