package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendBookingConfirmationEmail(String to, String resourceName) {

        try {

            log.info("Starting email send process...");

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(to);
            message.setSubject("Booking Confirmation");
            message.setText("Your booking for resource: " + resourceName + " has been confirmed.");

            log.info("Sending email to: {}", to);

            mailSender.send(message);

            log.info("Email sent successfully to: {}", to);

        } catch (Exception e) {
            log.error("Failed to send email to: {} Error: {}", to, e.getMessage(), e);
        }
    }
}
