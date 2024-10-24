package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepo extends JpaRepository<RestaurantTable, Long> {
    List<RestaurantTable> findByRestaurantIdAndAvailable(Long restaurantId, boolean available);
}