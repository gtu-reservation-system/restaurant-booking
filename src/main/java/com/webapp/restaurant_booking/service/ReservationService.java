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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    private boolean userHasReservationOnSameDay(User user, LocalDateTime reservationTime) {
        LocalDate reservationDate = reservationTime.toLocalDate();
        List<Reservation> userReservations = reservationRepo.findByUserId(user.getId());

        return userReservations.stream()
                .anyMatch(reservation -> reservation.getReservationStartTime().toLocalDate().isEqual(reservationDate));
    }

    @Transactional
    public Reservation addReservation(Map<String, Object> body) {
        Long restaurantId = ((Number) body.get("restaurantId")).longValue();
        Long userId = ((Number) body.get("userId")).longValue();
        LocalDateTime reservationStartTime = LocalDateTime.parse((String) body.get("reservationStartTime"));
        LocalDateTime reservationEndTime = reservationStartTime.plusHours(1);
        Integer numberOfPeople = ((Number) body.get("numberOfPeople")).intValue();
        String allergy= ((String) body.get("allergy"));
        String tag= ((String) body.get("tag"));

        validateReservationTime(reservationStartTime);

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (userHasReservationOnSameDay(user, reservationStartTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a reservation on this day.");
        }

        RestaurantTable availableTable = findAvailableTableForPartySize(restaurant, reservationStartTime, reservationEndTime, numberOfPeople);

        Reservation newReservation = new Reservation(restaurant, user, reservationStartTime, reservationEndTime, numberOfPeople, allergy, tag);
        newReservation.setTable(availableTable);
        return reservationRepo.save(newReservation);
    }

    private RestaurantTable findAvailableTableForPartySize(Restaurant restaurant,
                                                           LocalDateTime reservationStartTime,
                                                           LocalDateTime reservationEndTime,
                                                           Integer numberOfPeople) {
        List<RestaurantTable> availableTables = restaurant.getTables().stream()
                .filter(table -> table.getCapacity() >= numberOfPeople)
                .filter(table -> isTableAvailableForHour(table, reservationStartTime, reservationEndTime))
                .collect(Collectors.toList());

        if (availableTables.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No tables available for " + numberOfPeople + " people at this time.");
        }

        return availableTables.stream()
                .min(Comparator.comparingInt(RestaurantTable::getCapacity))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No suitable tables found."));
    }

    private boolean isTableAvailableForHour(RestaurantTable table,
                                            LocalDateTime reservationStartTime,
                                            LocalDateTime reservationEndTime) {
        List<Reservation> conflictingReservations = reservationRepo
                .findReservationsConflictingWithTimeSlot(
                        table.getId(),
                        reservationStartTime,
                        reservationEndTime
                );

        return conflictingReservations.isEmpty();
    }

    private boolean isTableAvailableAtTime(RestaurantTable table, LocalDateTime reservationTime) {
        LocalDateTime hourStart = reservationTime;
        LocalDateTime hourEnd = reservationTime.plusHours(1);

        List<Reservation> conflictingReservations = reservationRepo
                .findReservationsByRestaurantAndTimeBetween(
                        table.getRestaurant().getId(),
                        hourStart,
                        hourEnd
                );

        return conflictingReservations.stream()
                .noneMatch(r -> r.getTable().getId() == table.getId());
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
