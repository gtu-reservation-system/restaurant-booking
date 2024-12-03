package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Reservation;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationApiControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationApiController reservationApiController;

    private Reservation reservation;
    private Map<String, Object> reservationBody;

    @BeforeEach
    void setUp() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        User user = new User();
        user.setId(1L);
        user.setName("test user");

        reservation = new Reservation(restaurant, user, LocalDateTime.now().plusDays(1));
        reservation.setId(1L);

        reservationBody = new HashMap<>();
        reservationBody.put("restaurantId", 1L);
        reservationBody.put("userId", 1L);
        reservationBody.put("reservationTime", LocalDateTime.now().plusDays(1).toString());
    }

    @Test
    void addReservation_Success() {
        when(reservationService.addReservation(reservationBody)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationApiController.addReservation(reservationBody);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reservation, response.getBody());
        verify(reservationService).addReservation(reservationBody);
    }

    @Test
    void getAllReservations_Success() {
        List<Reservation> reservations = Collections.singletonList(reservation);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        List<Reservation> result = reservationApiController.getAllReservations();

        assertEquals(reservations, result);
        verify(reservationService).getAllReservations();
    }

    @Test
    void getSingleReservation_Success() {
        when(reservationService.getSingleReservation(1L)).thenReturn(Optional.of(reservation));

        Reservation result = reservationApiController.getSingleReservation(1L);

        assertEquals(reservation, result);
        verify(reservationService).getSingleReservation(1L);
    }

    @Test
    void getSingleReservation_NotFound() {
        when(reservationService.getSingleReservation(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> reservationApiController.getSingleReservation(1L));
        verify(reservationService).getSingleReservation(1L);
    }

    @Test
    void removeReservation_Success() {
        when(reservationService.removeReservation(1L)).thenReturn(true);

        boolean result = reservationApiController.removeReservation(1L);

        assertTrue(result);
        verify(reservationService).removeReservation(1L);
    }

    @Test
    void getReservationsByUser_Success() {
        List<Reservation> userReservations = Collections.singletonList(reservation);
        when(reservationService.getReservationsByUser(1L)).thenReturn(userReservations);

        List<Reservation> result = reservationApiController.getReservationsByUser(1L);

        assertEquals(userReservations, result);
        verify(reservationService).getReservationsByUser(1L);
    }

    @Test
    void getReservationsByRestaurant_Success() {
        List<Reservation> restaurantReservations = Collections.singletonList(reservation);
        when(reservationService.getReservationsByRestaurant(1L)).thenReturn(restaurantReservations);

        List<Reservation> result = reservationApiController.getReservationsByRestaurant(1L);

        assertEquals(restaurantReservations, result);
        verify(reservationService).getReservationsByRestaurant(1L);
    }
}