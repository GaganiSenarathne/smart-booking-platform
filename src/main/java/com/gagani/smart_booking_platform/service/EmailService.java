package com.gagani.smart_booking_platform.service;

public interface EmailService {

    void sendBookingConfirmationEmail(String to, String resourceName);
}
