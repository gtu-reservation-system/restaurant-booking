package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Reservation;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.RestaurantTable;
import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.ReservationRepo;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("/api/reservations")
@RestController
public class ReservationApiController {
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    RestaurantRepo restaurantRepo;

    @PostMapping
    public Reservation addReservation(@RequestBody Map<String, Object> body) {
        Long restaurantId = ((Number) body.get("restaurantId")).longValue();
        Long userId = ((Number) body.get("userId")).longValue();
        String reservationTimeString = (String) body.get("reservationTime");
        LocalDateTime reservationTime = LocalDateTime.parse(reservationTimeString);

        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
        User user = userRepo.findById(userId).get();

        Reservation newReservation = new Reservation();
        newReservation.setRestaurant(restaurant);
        newReservation.setUser(user);
        newReservation.setReservationTime(reservationTime);

        return reservationRepo.save(newReservation);
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