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
import java.util.stream.Collectors;

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

        validateReservationTime(reservationTime);

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        RestaurantTable availableTable = findAvailableTable(restaurant, reservationTime);

        availableTable.setAvailable(false);
        tableRepo.save(availableTable);

        Reservation newReservation = new Reservation(restaurant, user, reservationTime);
        newReservation.setTable(availableTable);
        return reservationRepo.save(newReservation);
    }

    private void validateReservationTime(LocalDateTime reservationTime) {
        LocalDateTime now = LocalDateTime.now();
        if (reservationTime.isBefore(now)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation time cannot be in the past");
        }
        if (reservationTime.isAfter(now.plusMonths(1))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservations can only be made up to 1 month in advance");
        }
    }

    private RestaurantTable findAvailableTable(Restaurant restaurant, LocalDateTime reservationTime) {
        List<RestaurantTable> availableTables = restaurant.getTables().stream()
                .filter(RestaurantTable::isAvailable)
                .filter(table -> isTableAvailableAtTime(table, reservationTime))
                .collect(Collectors.toList());

        if (availableTables.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No available tables at this restaurant and time.");
        }

        return availableTables.get(0);
    }

    private boolean isTableAvailableAtTime(RestaurantTable table, LocalDateTime reservationTime) {
        List<Reservation> existingReservations = reservationRepo.findReservationsByRestaurantAndTime(table.getRestaurant().getId(), reservationTime);

        return existingReservations.stream()
                .noneMatch(r -> r.getTable().getId() == table.getId());
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
