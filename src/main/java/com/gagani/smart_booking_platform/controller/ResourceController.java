package com.gagani.smart_booking_platform.controller;

import com.gagani.smart_booking_platform.dto.request.ResourceRequestDTO;
import com.gagani.smart_booking_platform.dto.response.ResourceResponseDTO;
import com.gagani.smart_booking_platform.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ResourceResponseDTO> resource(@RequestBody ResourceRequestDTO resourceRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(resourceService.createResource(resourceRequestDTO, email));
    }

    @GetMapping
    public Page<ResourceResponseDTO> getAllResources(Pageable pageable) {
        return resourceService.getAllResources(pageable);
    }

    @GetMapping("/{id}")
    public ResourceResponseDTO getResourceById(@PathVariable Long id) {
        return resourceService.getResourceById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResourceResponseDTO updateResource(@PathVariable Long id, @RequestBody ResourceRequestDTO resourceRequestDTO) {
        return resourceService.updateResource(id, resourceRequestDTO);
    }

    @GetMapping("/availableResources")
    public ResponseEntity<Page<ResourceResponseDTO>> getAvailableResources(@RequestParam Instant start, @RequestParam Instant end, Pageable pageable) {
        return ResponseEntity.ok(resourceService.getAvailableResources(start, end, pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ResourceResponseDTO> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
