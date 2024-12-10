package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.dto.PasswordResetRequestDTO;
import com.webapp.restaurant_booking.models.PasswordResetToken;
import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.PasswordResetTokenRepo;
import com.webapp.restaurant_booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordResetTokenRepo passwordResetTokenRepo;

    @Autowired
    private EmailService emailService; // TODO write actual email service

    private String generateResetToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public boolean initiatePasswordReset(PasswordResetRequestDTO request) {
        Optional<User> userOpt;

        if (request.getEmailOrPhone().contains("@")) {
            userOpt = userRepo.findByEmail(request.getEmailOrPhone());
        } else {
            userOpt = userRepo.findByPhoneNumber(request.getEmailOrPhone());
        }

        User user = userOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        passwordResetTokenRepo.findByUser(user).ifPresent(passwordResetTokenRepo::delete);

        String token = generateResetToken();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepo.save(resetToken);

        emailService.sendPasswordResetEmail(user.getEmail(), token);

        return true;
    }

    public boolean resetPassword(PasswordResetRequestDTO request) {
        Optional<PasswordResetToken> tokenOpt =
                passwordResetTokenRepo.findByToken(request.getResetToken());

        PasswordResetToken resetToken = tokenOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid reset token"));

        if (resetToken.isExpired() || resetToken.isUsed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reset token is expired or already used");
        }

        String newPassword = request.getNewPassword();


        User user = resetToken.getUser();
        user.setPassword(newPassword);
        userRepo.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepo.save(resetToken);

        return true;
    }
}