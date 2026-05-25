package com.gagani.smart_booking_platform.controller;

import com.gagani.smart_booking_platform.dto.response.RoleResponseDTO;
import com.gagani.smart_booking_platform.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<RoleResponseDTO>> findAllRoles(Pageable pageable) {
        return ResponseEntity.ok(roleService.findAllRoles(pageable));
    }

    @GetMapping("/byName")
    public ResponseEntity<RoleResponseDTO> findRoleByName(@RequestBody String name) {
        return ResponseEntity.ok(roleService.findRoleByName(name));
    }


}
