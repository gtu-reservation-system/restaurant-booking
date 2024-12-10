package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.dto.PasswordResetRequestDTO;
import com.webapp.restaurant_booking.models.PasswordResetToken;
import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.PasswordResetTokenRepo;
import com.webapp.restaurant_booking.repo.UserRepo;
import com.webapp.restaurant_booking.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/password")
public class PasswordResetApiContoller {

    @Autowired
    private PasswordResetTokenRepo passwordResetTokenRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequestDTO request){
        boolean is_initiated= passwordResetService.initiatePasswordReset(request);
        return ResponseEntity.ok("Password reset initiated");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequestDTO request){
        boolean is_reset= passwordResetService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }
}