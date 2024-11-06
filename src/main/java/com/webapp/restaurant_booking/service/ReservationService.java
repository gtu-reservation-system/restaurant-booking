package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.Reservation;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.RestaurantTable;
import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.ReservationRepo;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.TableRepo;
import com.webapp.restaurant_booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TableRepo tableRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Transactional
    public Reservation addReservation(Map<String, Object> body) {
        Long restaurantId = ((Number) body.get("restaurantId")).longValue();
        Long userId = ((Number) body.get("userId")).longValue();
        LocalDateTime reservationTime = LocalDateTime.parse((String) body.get("reservationTime"));

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        RestaurantTable table = restaurant.getTables().stream()
                .filter(RestaurantTable::isAvailable)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No available tables at this restaurant."));

        table.setAvailable(false);
        tableRepo.save(table);

        Reservation newReservation = new Reservation(restaurant, user, reservationTime);
        newReservation.setTable(table);
        return reservationRepo.save(newReservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    public Optional<Reservation> getSingleReservation(long id) {
        return reservationRepo.findById(id);
    }

    @Transactional
    public boolean removeReservation(long id) {
        Optional<Reservation> reservationOpt = reservationRepo.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();

            RestaurantTable table = reservation.getTable();
            if (table != null) {
                table.setAvailable(true);
                tableRepo.save(table);
            }
            User user = reservation.getUser();
            if (user != null) {
                user.getReservations().remove(reservation);
            }
            Restaurant restaurant = reservation.getRestaurant();
            if (restaurant != null) {
                restaurant.getReservations().remove(reservation);
            }
            return true;
        }
        return false;
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepo.findByUserId(userId);
    }

    public List<Reservation> getReservationsByRestaurant(Long restaurantId) {
        return reservationRepo.findByRestaurantId(restaurantId);
    }
}
