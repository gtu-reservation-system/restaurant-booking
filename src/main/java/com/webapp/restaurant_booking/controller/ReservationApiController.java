package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Reservation;
import com.webapp.restaurant_booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequestMapping("/api/reservations")
@RestController
public class ReservationApiController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, Object> body) {
        Reservation savedReservation = reservationService.addReservation(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping(value = "/{id}")
    public Reservation getSingleReservation(@PathVariable("id") long id) {
        return reservationService.getSingleReservation(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    @DeleteMapping(value = "/{id}")
    public boolean removeReservation(@PathVariable("id") long id) {
        return reservationService.removeReservation(id);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable("userId") Long userId) {
        return reservationService.getReservationsByUser(userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Reservation> getReservationsByRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        return reservationService.getReservationsByRestaurant(restaurantId);
    }
}
