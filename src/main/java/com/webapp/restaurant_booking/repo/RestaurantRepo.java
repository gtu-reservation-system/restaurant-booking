package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    @Query("SELECT DISTINCT r FROM Restaurant r WHERE :tag MEMBER OF r.tags")
    List<Restaurant> findByTag(@Param("tag") String tag);

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.menuItems mi WHERE LOWER(mi.name) LIKE LOWER(CONCAT('%', :menuItemName, '%'))")
    List<Restaurant> findByMenuItemNameContainingIgnoreCase(@Param("menuItemName") String menuItemName);

    Optional<Restaurant> findByEmail(String email);
}