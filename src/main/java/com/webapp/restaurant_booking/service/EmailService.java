package com.webapp.restaurant_booking.service;

import com.resend.*;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final Resend resend;
    private final String fromEmail;

    public EmailService(
            @Value("${resend.api.key}") String resendApiKey,
            @Value("${resend.from.email}") String fromEmail
    ) {
        this.resend = new Resend(resendApiKey);
        this.fromEmail = fromEmail;
    }

    public void sendPasswordResetEmail(String email, String resetToken) {
        try {
            String htmlContent = String.format("""
                    <html>
                        <body>
                            <h2>Password Reset Request</h2>
                            <p>token: </p>
                            <p style="font-size: 20px; font-weight: bold; color: #4a4a4a;">%s</p>
                            <p>token will expire in 15 minutes</p>
                        </body>
                    </html>
                    """, resetToken);

            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .from(fromEmail)
                    .to(email)
                    .subject("Password Reset Request")
                    .html(htmlContent)
                    .build();

            SendEmailResponse response = resend.emails().send(sendEmailRequest);

            if (response != null && response.getId() != null) {
                System.out.println("Password reset email sent successfully to: " + email);
            }
        } catch (Exception e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
}