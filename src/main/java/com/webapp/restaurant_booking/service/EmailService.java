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
                <body style="font-family: Arial, sans-serif; line-height: 1.6; background-color: #f9f9f9; margin: 0; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
                        <h2 style="text-align: center; color: #333333;">Password Reset Request</h2>
                        <p style="color: #555555; text-align: justify;">
                            You have requested to reset your password. Use the token below to complete the process.
                        </p>
                        <div style="text-align: center; margin: 20px 0;">
                            <p style="font-size: 24px; font-weight: bold; color: #4a4a4a; padding: 10px 20px; background: #f2f2f2; border: 1px solid #dddddd; border-radius: 5px;">%s</p>
                        </div>
                        <p style="color: #555555; text-align: justify;">
                            Please note that this token will expire in 15 minutes. If you did not request this, you can ignore this email.
                        </p>
                        <p style="color: #999999; text-align: center; font-size: 12px; margin-top: 20px;">
                            &copy; 2025 Rezerve. All rights reserved.
                        </p>
                    </div>
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
