package com.gagani.smart_booking_platform.config;

import com.gagani.smart_booking_platform.filter.JwtAuthFilter;
import com.gagani.smart_booking_platform.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfigBeans {

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtService jwtService) {
        return new JwtAuthFilter(jwtService);
    }
}
