package com.webapp.restaurant_booking.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @JsonManagedReference
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;

    @Column(nullable = false)
	private Integer numberOfPeople;

    @Column
    private String allergy;

    public Reservation() {
    }

    public Reservation(Restaurant restaurant, User user, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime, Integer numberOfPeople, String allergy) {
        this.restaurant = restaurant;
        this.user = user;
        this.reservationStartTime= reservationStartTime;
        this.reservationEndTime= reservationEndTime;
        this.numberOfPeople = numberOfPeople;
        this.allergy= allergy;
    }

    public Reservation(Restaurant restaurant, User user, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime, Integer numberOfPeople) {
        this.restaurant = restaurant;
        this.user = user;
        this.reservationStartTime= reservationStartTime;
        this.reservationEndTime= reservationEndTime;
        this.numberOfPeople = numberOfPeople;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }

    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    public void setReservationStartTime(LocalDateTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }

    public LocalDateTime getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(LocalDateTime reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

}
