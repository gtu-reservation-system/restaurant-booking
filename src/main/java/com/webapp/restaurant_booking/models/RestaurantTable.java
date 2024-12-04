package com.webapp.restaurant_booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private boolean available;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;

    public RestaurantTable() {
    }

    public RestaurantTable(String name, boolean available, int capacity, Restaurant restaurant) {
        this.name = name;
        this.available = available;
        this.capacity = capacity;
        this.restaurant = restaurant;
    }

    public RestaurantTable(long id, String name, boolean available, int capacity, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.capacity = capacity;
        this.restaurant = restaurant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
