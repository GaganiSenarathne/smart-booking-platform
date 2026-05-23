package com.gagani.smart_booking_platform.controller;

import com.gagani.smart_booking_platform.dto.request.UserRequestDTO;
import com.gagani.smart_booking_platform.dto.response.UserResponseDTO;
import com.gagani.smart_booking_platform.entity.AuthRequest;
import com.gagani.smart_booking_platform.service.JwtService;
import com.gagani.smart_booking_platform.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<UserResponseDTO> addNewUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO savedUser = userInfoService.createUser(userRequestDTO);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtService.generateToken(userDetails);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/user/profile")
    public UserResponseDTO getUserInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userInfoService.getCurrentUser(email);
    }



}
