package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
}
