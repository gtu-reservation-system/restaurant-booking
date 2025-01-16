package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.Reservation;
import com.webapp.restaurant_booking.models.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByRestaurantId(Long restaurantId);

    @Query("SELECT r FROM Reservation r WHERE r.restaurant.id = :restaurantId " +
            "AND r.reservationStartTime BETWEEN :start AND :end")
    List<Reservation> findReservationsByRestaurantAndTimeBetween(
            @Param("restaurantId") Long restaurantId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT r FROM Reservation r WHERE r.table.id = :tableId AND " +
            "((r.reservationStartTime < :endTime AND r.reservationEndTime > :startTime))")
    List<Reservation> findReservationsConflictingWithTimeSlot(
            @Param("tableId") Long tableId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT r FROM Reservation r WHERE r.reservationStartTime BETWEEN :start AND :end " +
            "AND r.status = :status")
    List<Reservation> findByReservationStartTimeBetweenAndStatus(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") ReservationStatus status
    );
}
