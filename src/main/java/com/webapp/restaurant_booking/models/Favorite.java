package com.webapp.restaurant_booking.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "restaurant_id")
    @JsonManagedReference
    private Restaurant restaurant;

    @Column
    private String restaurantName;

    public Favorite() {}

    public Favorite(User user, Restaurant restaurant, String restaurantName) {
        this.user = user;
        this.restaurant = restaurant;
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
