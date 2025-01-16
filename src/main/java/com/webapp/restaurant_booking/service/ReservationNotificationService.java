package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.Reservation;
import com.webapp.restaurant_booking.models.ReservationStatus;
import com.webapp.restaurant_booking.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationNotificationService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 * * * ?")
    public void sendReservationReminders() {
        LocalDateTime start = LocalDateTime.now().plusHours(23);
        LocalDateTime end = LocalDateTime.now().plusHours(24);

        List<Reservation> upcomingReservations = reservationRepo.findByReservationStartTimeBetweenAndStatus(
                start,
                end,
                ReservationStatus.APPROVED
        );

        for (Reservation reservation : upcomingReservations) {
            try {
                emailService.sendReservationReminderEmail(
                        reservation.getUser().getEmail(),
                        reservation.getRestaurant().getName(),
                        reservation.getReservationStartTime(),
                        reservation.getNumberOfPeople(),
                        reservation.getAllergy(),
                        reservation.getTag()
                );
            } catch (Exception e) {
                System.err.println("Failed to send reminder for reservation " + reservation.getId() + ": " + e.getMessage());
            }
        }
    }
}