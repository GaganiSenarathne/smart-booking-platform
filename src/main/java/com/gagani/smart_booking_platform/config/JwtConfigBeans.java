package com.gagani.smart_booking_platform.config;

import com.gagani.smart_booking_platform.filter.JwtAuthFilter;
import com.gagani.smart_booking_platform.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtConfigBeans {

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtService jwtService) {
        return new JwtAuthFilter(jwtService);
    }
}
