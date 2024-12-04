package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByRestaurantId(Long restaurantId);

	@Query("SELECT r FROM Reservation r WHERE r.restaurant.id = :restaurantId AND r.reservationTime = :time")
    List<Reservation> findReservationsByRestaurantAndTime(@Param("restaurantId") Long restaurantId, @Param("time") LocalDateTime time);

    @Query("SELECT r FROM Reservation r WHERE r.restaurant.id = :restaurantId " +
            "AND r.reservationTime BETWEEN :start AND :end")
    List<Reservation> findReservationsByRestaurantAndTimeBetween(
            @Param("restaurantId") Long restaurantId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
