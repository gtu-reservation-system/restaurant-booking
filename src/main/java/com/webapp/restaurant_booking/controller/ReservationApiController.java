package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Reservation;
import com.webapp.restaurant_booking.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/reservations")
@RestController
public class ReservationApiController {
    @Autowired
    private ReservationRepo reservationRepo;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    @GetMapping(value = "/{id}")
    public Reservation getSingleReservation(@PathVariable("id") long id){
        return reservationRepo.findById(id).get();
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable("id") Long id, @RequestBody Reservation reservationDetails) {
        Reservation reservation = reservationRepo.findById(id).get();

        reservation.setReservationTime(reservationDetails.getReservationTime());
        reservation.setRestaurant(reservationDetails.getRestaurant());
        reservation.setUser(reservationDetails.getUser());

        return reservationRepo.save(reservation);
    }

    @DeleteMapping(value = "/{id}")
    public boolean removeReservation(@PathVariable("id") long id){
        if(!reservationRepo.findById(id).equals(Optional.empty())){
            reservationRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable("userId") Long userId) {
        return reservationRepo.findByUserId(userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Reservation> getReservationsByRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        return reservationRepo.findByRestaurantId(restaurantId);
    }

}