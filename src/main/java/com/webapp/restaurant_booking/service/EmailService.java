package com.webapp.restaurant_booking.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendPasswordResetEmail(String email, String resetToken) {

        // TODO: write email sending logic

        System.out.println("__________________________ PASSWORD RESET TOKEN __________________________");
        System.out.println("Sending password reset email to: " + email);
        System.out.println("Reset Token: " + resetToken);
        System.out.println("__________________________________________________________________________");
    }
}