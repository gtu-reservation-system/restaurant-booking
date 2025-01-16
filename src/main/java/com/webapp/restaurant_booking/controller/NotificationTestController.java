package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.service.ReservationNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationTestController {

    @Autowired
    private ReservationNotificationService notificationService;

    @PostMapping("/test-reminder-notification")
    public ResponseEntity<String> testDailyReminders() {
        try {
            notificationService.sendReservationReminders();
            return ResponseEntity.ok("Daily reminders sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending reminders: " + e.getMessage());
        }
    }
}