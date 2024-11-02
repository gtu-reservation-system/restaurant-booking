package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByRestaurantId(Long restaurantId);
}