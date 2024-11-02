package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
}
