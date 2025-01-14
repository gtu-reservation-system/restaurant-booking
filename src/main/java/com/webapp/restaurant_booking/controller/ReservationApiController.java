package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.*;
import com.webapp.restaurant_booking.models.Reservation;
import com.webapp.restaurant_booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api/reservations")
@RestController
public class ReservationApiController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, Object> body) {
        try {
            System.out.println("Reservation Request Body: " + body);

            if (!body.containsKey("restaurantId")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant ID is required");
            }
            if (!body.containsKey("userId")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required");
            }
            if (!body.containsKey("reservationStartTime")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation time is required");
            }
            if (!body.containsKey("numberOfPeople")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of people is required");
            }
            Reservation savedReservation = reservationService.addReservation(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error processing reservation: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Reservation> updateReservationStatus(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> body) {
        try {
            String statusStr = body.get("status");
            if (statusStr == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required");
            }

            ReservationStatus newStatus = ReservationStatus.valueOf(statusStr.toUpperCase());
            Reservation updatedReservation = reservationService.updateReservationStatus(id, newStatus);
            return ResponseEntity.ok(updatedReservation);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
        }
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
