package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantId(Long restaurantId);

    @Query("SELECT mi FROM MenuItem mi WHERE :tag MEMBER OF mi.tags")
    List<MenuItem> findByTag(@Param("tag") String tag);

    List<MenuItem> findByNameContainingIgnoreCase(String name);
}