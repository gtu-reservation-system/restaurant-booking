package com.webapp.restaurant_booking.dto;

public class PasswordResetRequestDTO {
    private String emailOrPhone;
    private String resetToken;
    private String newPassword;

    public PasswordResetRequestDTO() {}

    public PasswordResetRequestDTO(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    public String getEmailOrPhone() {
        return emailOrPhone;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}